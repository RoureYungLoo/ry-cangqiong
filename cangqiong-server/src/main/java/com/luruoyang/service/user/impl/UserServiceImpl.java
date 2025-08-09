package com.luruoyang.service.user.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.luruoyang.dto.user.LoginDto;
import com.luruoyang.dto.user.WechatAuthDto;
import com.luruoyang.entity.Merchant;
import com.luruoyang.entity.User;
import com.luruoyang.mapper.user.UserMapper;
import com.luruoyang.properties.JJwtProperties;
import com.luruoyang.properties.WechatProperties;
import com.luruoyang.service.MerchantService;
import com.luruoyang.service.user.UserService;
import com.luruoyang.utils.JJwtUtils;
import com.luruoyang.utils.MapUtils;
import com.luruoyang.vo.LoginUserVo;
import io.jsonwebtoken.lang.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private WechatProperties wechatProperties;
  @Autowired
  private JJwtUtils jJwtUtils;
  @Autowired
  private JJwtProperties jJwtProperties;

  @Autowired
  private MerchantService merchantService;
  @Autowired
  private MapUtils mapUtils;

  @Override
  public LoginUserVo login(LoginDto loginDto) {

    /* auth wechat */
    WechatAuthDto wechatAuthDto = wechatLogin(loginDto);

    /* save fresh user */
    User userDB = userMapper.findByOpenId(wechatAuthDto.getOpenid());
    if (userDB == null) {
      userDB = new User();
      userDB.setOpenid(wechatAuthDto.getOpenid());
      userMapper.save(userDB);
    }
    /* 小程序端token */
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userDB.getId());
    claims.put("userId", userDB.getId());
    claims.put("openid", userDB.getOpenid());
    String token = jJwtUtils.gen(claims, jJwtProperties.getUserSecretKey(), jJwtProperties.getUserTtl());

    /* 计算配送费 */
    Merchant merchant = merchantService.findById(1L);
    String destination = mapUtils.getAddressLocation(merchant.getShopAddress());
    BigDecimal deliveryFee = mapUtils.computeDeliveryFee(loginDto.getLocation(), destination);

    log.warn("-------> user login {}", loginDto);

    LoginUserVo loginUserVo = LoginUserVo.builder()
        .id(userDB.getId())
        .token(token)
        .name("")
        .userName("")
        .deliveryFee(deliveryFee)
        .openid(wechatAuthDto.getOpenid())
        .shopAddress(merchant.getShopAddress())
        .shopId(1L)
        .shopTelephone(merchant.getShopTelephone())
        .build();

    loginUserVo.setMerchant(merchant);
    return loginUserVo;
  }

  /**
   * save user info
   *
   * @param user user
   * @return user saved
   */
  @Override
  @CachePut(value = "userCache", key = "#result.id")
  public User save(User user) {
    userMapper.save(user);
    log.info("save user {}", user);
    return user;
  }

  /**
   * get user info by userId
   *
   * @param userId userId
   * @return user
   */
  @Override
  @Cacheable(cacheNames = "userCache", key = "#userId", condition = "#userId != null")
  public User getById(Long userId) {
    User user = userMapper.findById(userId);
    if (ObjectUtil.isNull(user)) {
      throw new RuntimeException("用户不存在");
    }
    return user;
  }

  /**
   * delete user by userId
   *
   * @param userId userId
   * @return true or false
   */
  @Override
  @CacheEvict(cacheNames = "userCache", key = "#userId")
  public boolean deleteById(Long userId) {
    int deleted = userMapper.deleteByUserId(userId);
    return deleted > 0;
  }

  /**
   * delte all users
   *
   * @return true of false
   */
  @Override
  @CacheEvict(cacheNames = "userCache", allEntries = true)
  public boolean deleteAll() {
    int deleted = userMapper.deleteAll();
    return deleted >= 0;
  }

  private WechatAuthDto wechatLogin(LoginDto loginDto) {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    URIBuilder uriBuilder = null;
    try {
      uriBuilder = new URIBuilder(wechatProperties.getAuthUrl());
    } catch (URISyntaxException e) {
      log.error("new URIBuilder error: {}", e.getMessage());
      throw new RuntimeException(e);
    }
    uriBuilder.addParameter("appid", wechatProperties.getAppid());
    uriBuilder.addParameter("secret", wechatProperties.getSecret());
    uriBuilder.addParameter("grant_type", "authorization_code");

    uriBuilder.addParameter("js_code", loginDto.getCode());
    URI uri = null;
    try {
      uri = uriBuilder.build();
    } catch (URISyntaxException e) {
      log.error("uriBuilder.build error: {}", e.getMessage());
      throw new RuntimeException(e);
    }

    HttpGet httpGet = new HttpGet(uri);

    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(httpGet);
    } catch (IOException e) {
      log.error("httpClient.execute error: {}", e.getMessage());
      throw new RuntimeException(e);
    }

    StatusLine statusLine = response.getStatusLine();
    log.info("--------> wechat auth statusLine: {}", statusLine);
    HttpEntity entity = response.getEntity();
    String res = null;
    try {
      res = EntityUtils.toString(entity);
    } catch (IOException e) {
      log.error("EntityUtils.toString: error:{}", e.getMessage());
      throw new RuntimeException(e);
    }
    log.info("--------> wechat auth res: {}", res);
    WechatAuthDto wechatAuthDto = JSONObject.parseObject(res, WechatAuthDto.class);
    log.info("--------> wechat auth wechatAuthDto: {}", wechatAuthDto);

    return wechatAuthDto;
  }


}
