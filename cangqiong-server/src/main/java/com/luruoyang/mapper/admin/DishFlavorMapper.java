package com.luruoyang.mapper.admin;

import com.luruoyang.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
  int saveBatch(List<DishFlavor> flavors);

  int deleteBatch(List<Long> dishIds);

  List<DishFlavor> findByDishId(Long dishId);

  int deleteByDishId(Long dishId);
}
