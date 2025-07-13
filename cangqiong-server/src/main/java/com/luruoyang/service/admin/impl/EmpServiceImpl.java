package com.luruoyang.service.admin.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luruoyang.context.ThreadLocalContext;
import com.luruoyang.dto.EmpDto;
import com.luruoyang.dto.EmpPageDto;
import com.luruoyang.dto.LoginDto;
import com.luruoyang.entity.Employ;
import com.luruoyang.enums.Default;
import com.luruoyang.enums.EmpError;
import com.luruoyang.exception.BusinessException;
import com.luruoyang.mapper.admin.EmpMapper;
import com.luruoyang.properties.JJwtProperties;
import com.luruoyang.service.admin.EmpService;
import com.luruoyang.utils.JJwtUtils;
import com.luruoyang.utils.PageResult;
import com.luruoyang.vo.LoginUserVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

  @Autowired
  private EmpMapper empMapper;

  @Autowired
  private JJwtUtils jwtUtils;
  @Autowired
  private JJwtProperties jJwtProperties;

  @Override
  public LoginUserVo login(LoginDto loginDto) {
    Employ emp = empMapper.findEmpByUserName(loginDto.getUsername());

    /* 用户名不存在 */
    if (Objects.isNull(emp)) return null;

    /* 用户名或密码错误 */
    String pwdDto = loginDto.getPassword();
    String digestDB = emp.getPassword();
    String digest = DigestUtils.md5DigestAsHex(pwdDto.getBytes(StandardCharsets.UTF_8));
    if (!(digest.equals(digestDB))) return null;

    log.info("{}:{}", digest, digestDB);
    /* 账号被锁定 */

    Map<String, Object> claims = new HashMap<>();
    claims.put("id", emp.getId());
    claims.put("name", emp.getName());
    claims.put("username", emp.getUsername());

    String token = jwtUtils.gen(claims, jJwtProperties.getAdminSecret(), jJwtProperties.getAdminExpiration());
    return LoginUserVo.builder()
        .id(emp.getId())
        .name(emp.getName())
        .userName(emp.getUsername())
        .token(token)
        .build();
  }

  @Override
  public boolean saveEmp(EmpDto empDto) {
    Employ empByUserName = empMapper.findEmpByUserName(empDto.getUsername());
    if (Objects.nonNull(empByUserName)) throw new BusinessException(EmpError.USER_EXISTS);

    Employ employ = new Employ();

    BeanUtils.copyProperties(empDto, employ);
    if (employ.getPassword() == null) employ.setPassword(Default.PWD.getPwd());
    if (employ.getStatus() == null) employ.setStatus(1);

    /*employ.setCreateTime(LocalDateTime.now());
    employ.setUpdateTime(LocalDateTime.now());

    Long curUserId = ThreadLocalContext.get();
    employ.setCreateUser(curUserId);
    employ.setUpdateUser(curUserId);*/

    return empMapper.saveEmp(employ) == 1;
  }

  /**
   * 分页查询
   *
   * @param empPageDto
   * @return
   */
  @Override
  public PageResult findEmpPage(EmpPageDto empPageDto) {

    PageHelper.startPage(empPageDto.getPage(), empPageDto.getPageSize());

    List<Employ> employList = empMapper.findEmpPage(empPageDto);
    Page<Employ> employPage = (Page<Employ>) employList;


    return PageResult.getPageResult(employPage.getTotal(), employPage.getResult());
  }

  @Override
  public boolean changeStataus(Integer status, Long id) {
    LocalDateTime updateTime = LocalDateTime.now();
    Long updateUser = ThreadLocalContext.get();
    int row = empMapper.changeStatus(status, id, updateTime, updateUser);

    return row == 1;
  }

  @Override
  public Employ findEmpById(Long empId) {
    Employ empById = empMapper.findEmpById(empId);
    empById.setPassword("");
    return empById;
  }

  @Override
  public boolean updateById(EmpDto empDto) {

    Employ empDB = empMapper.findEmpById(empDto.getId());
    if (Objects.isNull(empDB)) return false;

    BeanUtils.copyProperties(empDto, empDB);

    /*empDB.setUpdateTime(LocalDateTime.now());
    empDB.setUpdateUser(ThreadLocalContext.get());*/

    int row = empMapper.updateById(empDB);
    return row == 1;
  }
}
