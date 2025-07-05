package com.luruoyang.dto;

import lombok.Data;

@Data
public class DishPageDto {
  private String name;
  private Long categoryId;
  private Integer status;
  private Long page;
  private Long pageSize;
}

