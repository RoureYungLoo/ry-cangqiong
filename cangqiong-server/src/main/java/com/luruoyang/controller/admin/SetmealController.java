package com.luruoyang.controller.admin;

import com.luruoyang.dto.SetmealDto;
import com.luruoyang.service.admin.SetmealService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import com.luruoyang.vo.SetmealVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Tag(name = "套餐管理")
public class SetmealController {

  @Autowired
  private SetmealService setmealService;

  @PostMapping
  @Operation(summary = "添加套餐")
  public Result save(@RequestBody SetmealDto setmealDto) {
    if (setmealService.save(setmealDto)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @Operation(summary = "分页查询")
  @GetMapping("/page")
  public Result findPage(SetmealDto setmealDto) {
    PageResult page = setmealService.findPage(setmealDto);

    return Result.success(page);
  }

  @DeleteMapping
  @Operation(summary = "批量删除")
  public Result deleteByIds(@RequestParam("ids") List<Long> ids) {
    if (setmealService.deleteBatch(ids)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @Operation(summary = "根据ID查询")
  @GetMapping("/{setmealId}")
  public Result findById(@PathVariable("setmealId") Long setmealId) {
    SetmealVo setmealVo = setmealService.findById(setmealId);
    if (setmealVo != null) {
      return Result.success(setmealVo);
    } else {
      return Result.fail();
    }

  }

  @Operation(summary = "更新套餐")
  @PutMapping
  public Result updateById(@RequestBody SetmealDto setmealDto) {
    if (setmealService.updateById(setmealDto)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @Operation(summary = "起售/停售")
  @PostMapping("/status/{status}")
  public Result updateStatus(@RequestParam("id") Long setmealId, @PathVariable("status") Integer status) {
    SetmealDto setmealDto = new SetmealDto();
    setmealDto.setId(setmealId);
    setmealDto.setStatus(status);
    if (setmealService.updateStatusById(setmealDto)) {
      return Result.success();
    } else {
      return Result.fail();
    }

  }
}