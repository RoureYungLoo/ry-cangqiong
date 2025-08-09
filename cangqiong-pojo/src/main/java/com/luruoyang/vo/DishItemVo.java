package com.luruoyang.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishItemVo implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer copies;
  private String description;
  private String image;
  private String name;
}
