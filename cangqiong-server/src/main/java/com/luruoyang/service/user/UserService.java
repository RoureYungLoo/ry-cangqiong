package com.luruoyang.service.user;

import com.luruoyang.dto.user.LoginDto;
import com.luruoyang.vo.LoginUserVo;

public interface UserService {
  LoginUserVo login(LoginDto loginDto);
}
