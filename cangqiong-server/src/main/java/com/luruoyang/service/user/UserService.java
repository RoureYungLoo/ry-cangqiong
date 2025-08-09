package com.luruoyang.service.user;

import com.luruoyang.dto.user.LoginDto;
import com.luruoyang.entity.User;
import com.luruoyang.vo.LoginUserVo;

/**
 * @author luruoyang
 */
public interface UserService {
  LoginUserVo login(LoginDto loginDto);

  User save(User user);

  User getById(Long userId);

  boolean deleteById(Long userId);

  boolean deleteAll();

}
