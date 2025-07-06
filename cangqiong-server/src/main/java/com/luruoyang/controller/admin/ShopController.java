package com.luruoyang.controller.admin;

import com.luruoyang.constant.RedisKey;
import com.luruoyang.enums.ShopStatus;
import com.luruoyang.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name = "店铺状态管理")
public class ShopController {

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;


  @Operation(summary = "更新店铺状态")
  @PutMapping("/shop/{status}")
  public Result updateShopStatus(@PathVariable("status") Integer status) {
    redisTemplate.opsForValue().set(RedisKey.SHOP_STATUS, status);
    return Result.success();
  }

  @Operation(summary = "查询店铺状态")
  @GetMapping("/shop/status")
  public Result getShopStatus() {
    Object o = redisTemplate.opsForValue().get(RedisKey.SHOP_STATUS);

    if (o == null) {
      log.warn("获取店铺状态失败{}", o);
      return Result.fail();
    }
    int status = Integer.parseInt(o.toString());
    return Result.success(status);

  }
}
