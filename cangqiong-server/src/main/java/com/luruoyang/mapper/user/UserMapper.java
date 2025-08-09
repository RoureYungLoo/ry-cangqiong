package com.luruoyang.mapper.user;

import com.luruoyang.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  int save(User user);

  int updateUserById(User user);

  int deleteUserById(User user);

  int deleteByUserId(Long userId);

  User findById(Long userId);

  User findByOpenId(String openid);

  int deleteAll();
}
