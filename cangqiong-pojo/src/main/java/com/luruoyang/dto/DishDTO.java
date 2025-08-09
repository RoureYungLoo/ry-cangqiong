package com.luruoyang.dto;

import com.luruoyang.entity.Dish;
import com.luruoyang.entity.DishFlavor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DishDTO extends Dish implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private List<DishFlavor> flavors = new ArrayList<>();

  private String page;
  private String pageSize;

}