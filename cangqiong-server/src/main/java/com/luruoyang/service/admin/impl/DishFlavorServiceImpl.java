package com.luruoyang.service.admin.impl;

import com.luruoyang.entity.DishFlavor;
import com.luruoyang.mapper.admin.DishFlavorMapper;
import com.luruoyang.service.admin.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DishFlavorServiceImpl implements DishFlavorService {

  @Autowired
  private DishFlavorMapper dishFlavorMapper;

  @Override
  public boolean saveBatch(List<DishFlavor> flavors) {

    return flavors.size() == dishFlavorMapper.saveBatch(flavors);

  }

  @Override
  public boolean deleteBatch(List<Long> dishIds) {
    int deleted = dishFlavorMapper.deleteBatch(dishIds);
    return deleted > 0;
  }

  @Override
  public List<DishFlavor> findByDishId(Long dishId) {
    List<DishFlavor> dishFlavors = dishFlavorMapper.findByDishId(dishId);
    return dishFlavors;
  }

  @Override
  public boolean deleteByDishId(Long dishId) {
    int row = dishFlavorMapper.deleteByDishId(dishId);
    return row > 0;
  }
}
