package com.luruoyang.dto.user;

import lombok.Data;

@Data
public class ShoppingCartDto {
  private String dishFlavor;
  private Long dishId;
  private Long setmealId;
}
