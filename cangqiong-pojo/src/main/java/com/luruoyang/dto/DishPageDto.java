package com.luruoyang.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class DishPageDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private String name;
  private Long categoryId;
  private Integer status;
  private Long page;
  private Long pageSize;
}

