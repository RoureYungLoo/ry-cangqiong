package com.luruoyang.service.admin;

import com.luruoyang.dto.EmpDto;
import com.luruoyang.dto.EmpPageDto;
import com.luruoyang.dto.LoginDto;
import com.luruoyang.entity.Employ;
import com.luruoyang.utils.PageResult;
import com.luruoyang.vo.LoginUserVo;

public interface EmpService {
  LoginUserVo login(LoginDto loginDto);


  boolean saveEmp(EmpDto empDto);

  PageResult findEmpPage(EmpPageDto empPageDto);

  boolean changeStataus(Integer status, Long id);

  Employ findEmpById(Long empId);

  boolean updateById(EmpDto empDto);
}
