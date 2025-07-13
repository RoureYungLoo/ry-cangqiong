package com.luruoyang.service.admin;

import com.luruoyang.dto.SetmealDto;
import com.luruoyang.entity.Setmeal;
import com.luruoyang.utils.PageResult;
import com.luruoyang.vo.SetmealVo;

import java.util.List;

public interface SetmealService {
  boolean save(SetmealDto setmealDto);

  PageResult findPage(SetmealDto setmealDto);

  boolean deleteBatch(List<Long> ids);

  SetmealVo findById(Long setmealid);

  boolean updateById(SetmealDto setmealDto);

  boolean updateStatusById(SetmealDto setmealDto);

  List<Setmeal> findByCategoryId(Long categoryId);
}
