package com.luruoyang.service.admin;

import com.luruoyang.entity.Setmeal;
import com.luruoyang.entity.SetmealDish;

import java.util.List;

public interface SetmealDishService {
  int saveBatch(List<SetmealDish> setmealDishList);


  int deleteSetmealDishBySetmealId(List<Long> setmealIds);

  Long findSetmealCountByDishId(List<Long> ids);
}
