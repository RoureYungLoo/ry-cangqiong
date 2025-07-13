package com.luruoyang.controller.user;

import com.luruoyang.constant.RedisKey;
import com.luruoyang.entity.Merchant;
import com.luruoyang.service.MerchantService;
import com.luruoyang.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "店铺接口")
public class ShopController2 {


  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;
  @Autowired
  private MerchantService merchantService;


  @Operation(summary = "获取店铺营业状态")
  @GetMapping("/shop/status")
  public Result getShopStatus() {
    Object o = redisTemplate.opsForValue().get(RedisKey.SHOP_STATUS);
    log.warn("获取店铺营业状态: {}", o);
    if (o == null) {
      log.warn("获取店铺营业状态失败, {}", o);
      return Result.fail();
    }
    return Result.success(Integer.parseInt(o.toString()));
  }

  @Operation(summary = "获取店铺信息")
  @GetMapping("/shop/getMerchantInfo")
  public Result getMerchantInfo() {
    Merchant merchant = merchantService.findById(1L);
    return Result.success(merchant);
  }
}
