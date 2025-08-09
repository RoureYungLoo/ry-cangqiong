package com.luruoyang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish implements Serializable {
  private static final long serialVersionUID = 1L;
  protected Long id;
  protected String name;
  protected Long categoryId;
  protected BigDecimal price;
  protected String image;
  protected String description;
  protected Integer status;
  protected LocalDateTime createTime;
  protected LocalDateTime updateTime;
  protected Long createUser;
  protected Long updateUser;
  protected Integer makeTime;
}
