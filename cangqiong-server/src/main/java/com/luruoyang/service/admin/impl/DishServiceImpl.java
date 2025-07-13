package com.luruoyang.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luruoyang.context.ThreadLocalContext;
import com.luruoyang.dto.DishDTO;
import com.luruoyang.dto.DishPageDto;
import com.luruoyang.entity.Category;
import com.luruoyang.entity.Dish;
import com.luruoyang.entity.DishFlavor;
import com.luruoyang.enums.DishError;
import com.luruoyang.enums.StatusEnum;
import com.luruoyang.exception.BusinessException;
import com.luruoyang.mapper.admin.DishMapper;
import com.luruoyang.service.admin.*;
import com.luruoyang.utils.PageResult;
import com.luruoyang.vo.DishVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

  @Autowired
  private DishMapper dishMapper;

  @Autowired
  private DishFlavorService dishFlavorService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private SetmealService setmealService;
  @Autowired
  private SetmealDishService setmealDishService;

  @Override
  public boolean saveDish(DishDTO dishDTO) {

    Dish dish = new Dish();

    BeanUtils.copyProperties(dishDTO, dish);

    int row = dishMapper.saveDish(dish);

    List<DishFlavor> flavors = dishDTO.getFlavors();

    /* 设置 dishId, 主键已经自动生成 */
    flavors.forEach(flavor -> flavor.setDishId(dish.getId()));

    boolean affected = dishFlavorService.saveBatch(flavors);

    return row == 1 && affected;
  }

  @Override
  public PageResult findPage(DishPageDto dishPageDto) {
    PageHelper.startPage(Math.toIntExact(dishPageDto.getPage()), Math.toIntExact(dishPageDto.getPageSize()));

    List<DishVo> dishVoList = dishMapper.findPage(dishPageDto);


    Page<DishVo> page = (Page<DishVo>) dishVoList;
    return PageResult.getPageResult(page.getTotal(), page.getResult());
  }

  @Override
  public boolean updateStatusById(Long id, Integer status) {
    LocalDateTime now = LocalDateTime.now();
    Long currentUser = ThreadLocalContext.get();

    int row = dishMapper.updateStatusById(id, status, now, currentUser);

    return row == 1;
  }

  @Override
  public boolean deleteBatch(List<Long> ids) {
    /* 起售的不能删 */
    Long count = dishMapper.findCount(ids, StatusEnum.ENABLE.getValue());
    if (count > 0) {
      throw new BusinessException(DishError.ERR_DISH_ONSALE.getErrMsg());
    }

    /* 关联套餐的不能删 */
    count = setmealDishService.findSetmealCountByDishId(ids);
    if (count > 0) {
      throw new BusinessException(DishError.ERR_DISH_IN_SETMAEL.getErrMsg());
    }

    int row = dishMapper.deleteBatch(ids);

    boolean flag = dishFlavorService.deleteBatch(ids);

    return row > 0 && flag;
  }

  @Override
  public DishVo findById(Long id) {
    Dish dish = dishMapper.findById(id);
    DishVo dishVo = new DishVo();
    BeanUtils.copyProperties(dish, dishVo);

    Category category = categoryService.findById(dishVo.getCategoryId());

    List<DishFlavor> dishFlavors = dishFlavorService.findByDishId(id);

    dishVo.setCategoryName(category.getName());
    dishVo.setFlavors(dishFlavors);
    return dishVo;
  }

  @Override
  public boolean updateById(DishDTO dishDTO) {
    int row = dishMapper.updateById(dishDTO);

    Long dishId = dishDTO.getId();
    dishFlavorService.deleteByDishId(dishId);

    List<DishFlavor> flavors = dishDTO.getFlavors();
    flavors.forEach(f -> f.setDishId(dishId));

    dishFlavorService.saveBatch(dishDTO.getFlavors());

    return true;
  }

  @Override
  public List<DishVo> findByCategoryId(Long categoryId) {
    Dish dish = new Dish();
    dish.setCategoryId(categoryId);
    dish.setStatus(1);
    List<DishVo> dishVoList = dishMapper.findByCategoryId(dish);

    return dishVoList;

  }
}
