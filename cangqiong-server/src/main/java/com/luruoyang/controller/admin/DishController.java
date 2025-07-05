package com.luruoyang.controller.admin;


import com.luruoyang.dto.DishDTO;
import com.luruoyang.dto.DishPageDto;
import com.luruoyang.entity.Dish;
import com.luruoyang.service.admin.DishService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import com.luruoyang.vo.DishVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Tag(name = "菜品管理")
public class DishController {

  @Autowired
  private DishService dishService;

  @PostMapping
  @Operation(summary = "添加菜品")
  public Result saveDish(@RequestBody DishDTO dishDTO) {
    if (dishService.saveDish(dishDTO)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @GetMapping("/page")
  @Operation(summary = "条件分页查询")
  public Result pageDish(DishPageDto dishPageDto) {
    PageResult result = dishService.findPage(dishPageDto);
    return Result.success(result);
  }

  @PostMapping("/status/{status}")
  @Operation(summary = "更新菜品status")
  public Result updateStatus(@RequestParam("id") Long id, @PathVariable("status") Integer status) {
    if (dishService.updateStatusById(id, status)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @Operation(summary = "根据菜品ID批量删除")
  @DeleteMapping
  public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
    if (dishService.deleteBatch(ids)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @GetMapping("/{id}")
  @Operation(summary = "根据菜品ID查询")
  public Result findById(@PathVariable("id") Long id) {
    DishVo dishVo = dishService.findById(id);
    return Result.success(dishVo);
  }

  @PutMapping
  @Operation(summary = "根据DishID查询")
  public Result updateById(@RequestBody DishDTO dishDTO) {
    if (dishService.updateById(dishDTO)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @GetMapping("/list")
  @Operation(summary = "根据ID查询所有菜品")
  public Result findByCategoryId(@RequestParam("categoryId") Long categoryId) {
    List<Dish> result = dishService.findByCategoryId(categoryId);

    return Result.success(result);

  }
}
