package com.luruoyang.controller.user;

import com.luruoyang.dto.user.ShoppingCartDto;
import com.luruoyang.entity.ShoppingCart;
import com.luruoyang.service.user.ShoppingCartService;
import com.luruoyang.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {

  @Autowired
  private ShoppingCartService shoppingCartService;

  @PostMapping("/add")
  @Operation(summary = "加购")
  public Result addShoppingCart(@RequestBody ShoppingCartDto dto) {
    shoppingCartService.addShoppingCart(dto);
    return Result.success();
  }

  @PostMapping("/sub")
  @Operation(summary = "减购")
  public Result subShoppingCart(@RequestBody ShoppingCartDto dto) {
    shoppingCartService.subShoppingCart(dto);
    return Result.success();
  }

  @DeleteMapping("/clean")
  @Operation(summary = "清空当前用户的购物车")
  public Result cleanShoppingCart() {
    shoppingCartService.cleanShoppingCart();
    return Result.success();
  }

  @GetMapping("/list")
  @Operation(summary = "查询购物车所有内容")
  public Result list(ShoppingCartDto dto) {
    List<ShoppingCart> list = shoppingCartService.list(dto);
    return Result.success(list);
  }


}
