package com.luruoyang.controller.admin;

import com.luruoyang.dto.EmpDto;
import com.luruoyang.dto.EmpPageDto;
import com.luruoyang.dto.LoginDto;
import com.luruoyang.entity.Employ;
import com.luruoyang.enums.ResCode;
import com.luruoyang.service.admin.EmpService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import com.luruoyang.vo.LoginUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工管理")
public class EmpController {

  @Autowired
  private EmpService empService;

  @Operation(summary = "员工登录")
  @PostMapping("/login")
  public Result login(@RequestBody LoginDto loginDto) {
    LoginUserVo lvo = empService.login(loginDto);
    Result success = Result.success(lvo);
    return success;
  }

  @PostMapping("/logout")
  @Operation(summary = "退出登录")
  public Result logout() {
    return Result.success();
  }

  @PostMapping
  @Operation(summary = "新增员工")
  public Result saveEmp(@RequestBody EmpDto empDto) {
    boolean res = empService.saveEmp(empDto);
    return Result.success(res);
  }

  @GetMapping("/page")
  @Operation(summary = "分页查询")
  public Result findEmpPage(EmpPageDto empPageDto) {

    PageResult result = empService.findEmpPage(empPageDto);
    return Result.success(result);
  }

  @Operation(summary = "账号禁用/启用")
  @PostMapping("/status/{status}")
  public Result changeStatus(@PathVariable("status") Integer status, @RequestParam("id") Long id) {
    if (empService.changeStataus(status, id)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @Operation(summary = "根据ID查询")
  @GetMapping("/{empId}")
  public Result findEmpById(@PathVariable("empId") Long empId) {
    Employ employ = empService.findEmpById(empId);
    return Result.success(employ);
  }

  @Operation(summary = "根据ID更新")
  @PutMapping
  public Result updateEmpById(@RequestBody EmpDto empDto) {
    boolean flag = empService.updateById(empDto);
    if (flag) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @GetMapping("/hello")
  public void hello() {
    System.out.println("hello");
  }
}
