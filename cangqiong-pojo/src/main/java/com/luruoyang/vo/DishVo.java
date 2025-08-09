package com.luruoyang.vo;

import com.luruoyang.entity.Dish;
import com.luruoyang.entity.DishFlavor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DishVo extends Dish implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<DishFlavor> flavors;
  private String categoryName;
}
