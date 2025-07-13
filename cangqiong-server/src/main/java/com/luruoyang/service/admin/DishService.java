package com.luruoyang.service.admin;

import com.luruoyang.dto.DishDTO;
import com.luruoyang.dto.DishPageDto;
import com.luruoyang.entity.Dish;
import com.luruoyang.utils.PageResult;
import com.luruoyang.vo.DishVo;

import java.util.List;

public interface DishService {
  boolean saveDish(DishDTO dishDTO);

  PageResult findPage(DishPageDto dishPageDto);

  boolean updateStatusById(Long id, Integer status);

  boolean deleteBatch(List<Long> ids);

  DishVo findById(Long id);

  boolean updateById(DishDTO dishDTO);

  List<DishVo> findByCategoryId(Long categoryId);
}
