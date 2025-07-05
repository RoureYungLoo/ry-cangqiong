package com.luruoyang.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SetmealDish {
  private Long id;
  private Long setmealId;
  private Long dishId;
  private String name;
  private BigDecimal price;
  private Integer copies;
}
