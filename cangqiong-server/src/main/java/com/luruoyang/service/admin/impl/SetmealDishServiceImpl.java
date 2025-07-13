package com.luruoyang.service.admin.impl;

import com.luruoyang.entity.SetmealDish;
import com.luruoyang.mapper.admin.SetmealDishMapper;
import com.luruoyang.service.admin.SetmealDishService;
import com.luruoyang.vo.DishItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl implements SetmealDishService {

  @Autowired
  protected SetmealDishMapper setmealDishMapper;


  @Override
  public int saveBatch(List<SetmealDish> setmealDishList) {
    int row = setmealDishMapper.saveBatch(setmealDishList);
    return row;
  }

  @Override
  public int deleteSetmealDishBySetmealId(List<Long> setmealIds) {
    int row = setmealDishMapper.deleteSetmealDishBySetmealId(setmealIds);
    return row;
  }

  @Override
  public Long findSetmealCountByDishId(List<Long> ids) {
    return setmealDishMapper.findSetmealCountByDishId(ids);
  }

  @Override
  public List<DishItemVo> findDishBySetmealId(Long setmealId) {
    return setmealDishMapper.findDishBySetmealId(setmealId);
  }
}
