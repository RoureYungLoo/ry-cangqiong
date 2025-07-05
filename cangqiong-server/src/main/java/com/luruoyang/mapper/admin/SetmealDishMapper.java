package com.luruoyang.mapper.admin;

import com.luruoyang.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SetmealDishMapper {
  int saveBatch(List<SetmealDish> setmealDishList);

  int deleteSetmealDishBySetmealId(List<Long> setmealIds);

  Long findSetmealCountByDishId(List<Long> ids);
}
