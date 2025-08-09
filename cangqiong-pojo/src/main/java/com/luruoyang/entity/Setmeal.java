package com.luruoyang.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Setmeal implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  protected Long id;
  protected Long categoryId;
  protected String name;
  protected BigDecimal price;
  protected Integer status;
  protected String description;
  protected String image;
  protected LocalDateTime createTime;
  protected LocalDateTime updateTime;
  protected Long createUser;
  protected Long updateUser;
  protected Integer makeTime;
}
