package com.luruoyang.service.admin;

import com.luruoyang.entity.DishFlavor;

import java.util.List;

public interface DishFlavorService {
  boolean saveBatch(List<DishFlavor> flavors);

  boolean deleteBatch(List<Long> dishIds);

  List<DishFlavor> findByDishId(Long id);

  boolean deleteByDishId(Long dishId);
}
