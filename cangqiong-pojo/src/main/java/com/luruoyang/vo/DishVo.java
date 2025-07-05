package com.luruoyang.vo;

import com.luruoyang.entity.Dish;
import com.luruoyang.entity.DishFlavor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DishVo extends Dish {
  private List<DishFlavor> flavors;
  private String categoryName;
}
