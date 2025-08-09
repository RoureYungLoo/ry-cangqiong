package com.luruoyang.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishFlavor implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long dishId;
  private String name;
  private String value;
}
