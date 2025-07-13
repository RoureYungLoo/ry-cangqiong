package com.luruoyang.controller.user;

import com.luruoyang.dto.user.LoginDto;
import com.luruoyang.entity.Category;
import com.luruoyang.entity.Dish;
import com.luruoyang.entity.Merchant;
import com.luruoyang.entity.Setmeal;
import com.luruoyang.service.MerchantService;
import com.luruoyang.service.admin.CategoryService;
import com.luruoyang.service.admin.DishService;
import com.luruoyang.service.admin.SetmealDishService;
import com.luruoyang.service.admin.SetmealService;
import com.luruoyang.service.user.UserService;
import com.luruoyang.utils.HttpUtils;
import com.luruoyang.utils.MapUtils;
import com.luruoyang.utils.Result;
import com.luruoyang.vo.DishItemVo;
import com.luruoyang.vo.DishVo;
import com.luruoyang.vo.LoginUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "小程序用户接口")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private DishService dishService;
  @Autowired
  private SetmealService setmealService;
  @Autowired
  private SetmealDishService setmealDishService;


  @PostMapping("/user/login")
  @Operation(summary = "用户登录")
  public Result login(@RequestBody LoginDto loginDto) {
    LoginUserVo loginUserVo = userService.login(loginDto);
    return Result.success(loginUserVo);
  }

  @Operation(summary = "查询分类")
  @GetMapping("/category/list")
  public Result categoryList() {
    List<Category> categoryList = categoryService.list(null, null);

    return Result.success(categoryList);
  }

  @Operation(summary = "根据分类id查询菜品")
  @GetMapping("/dish/list")
  public Result dishList(@RequestParam("categoryId") Long categoryId) {
    List<DishVo> dishVoList = dishService.findByCategoryId(categoryId);
    return Result.success(dishVoList);
  }

  @Operation(summary = "根据分类id查询套餐")
  @GetMapping("/setmeal/list")
  public Result setmealList(@RequestParam("categoryId") Long categoryId) {
    List<Setmeal> setmealList = setmealService.findByCategoryId(categoryId);
    return Result.success(setmealList);
  }

  @Operation(summary = "根据套餐id查询包含的菜品")
  @GetMapping("/setmeal/dish/{setmealId}")
  public Result findDishBySetmealId(@PathVariable("setmealId") Long setmealId) {
    List<DishItemVo> dishItemVoList = setmealDishService.findDishBySetmealId(setmealId);
    return Result.success(dishItemVoList);
  }
}
