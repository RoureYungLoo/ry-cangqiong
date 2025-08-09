package com.luruoyang.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "购物车")
public class ShoppingCart {
  private Long id;
  private String name;
  private String image;
  private Long userId;
  private Long dishId;
  private Long setmealId;
  private String dishFlavor;
  @SchemaProperty(name = "单价")
  private BigDecimal amount;
  @SchemaProperty(name = "数量")
  private Integer number;
  private LocalDateTime createTime;
}
