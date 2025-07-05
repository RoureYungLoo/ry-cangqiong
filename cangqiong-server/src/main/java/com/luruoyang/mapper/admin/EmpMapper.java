package com.luruoyang.mapper.admin;

import com.luruoyang.annotation.AutoFill;
import com.luruoyang.dto.EmpPageDto;
import com.luruoyang.entity.Employ;
import com.luruoyang.enums.OpType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EmpMapper {


  Employ findEmpByUserName(String username);

  @AutoFill(OpType.INSERT)
  int saveEmp(Employ employ);

  List<Employ> findEmpPage(EmpPageDto empPageDto);


  int changeStatus(
      @Param("status") Integer status,
      @Param("empId") Long id,
      @Param("updateTime") LocalDateTime updateTime,
      @Param("updateUser") Long updateUser);

  Employ findEmpById(Long empId);

  @AutoFill(OpType.UPDATE)
  int updateById(Employ employ);
}
