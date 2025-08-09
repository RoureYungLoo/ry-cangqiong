package com.luruoyang.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luruoyang.constant.RedisKey;
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
import com.luruoyang.utils.KeyUtil;
import com.luruoyang.utils.PageResult;
import com.luruoyang.vo.DishVo;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.swing.text.Utilities;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  /**
   * 新增菜品
   *
   * @param dishDTO dto
   * @return 结果
   */
  @Override
  public boolean saveDish(DishDTO dishDTO) {
    Dish dish = new Dish();
    BeanUtils.copyProperties(dishDTO, dish);
    int row = dishMapper.saveDish(dish);
    List<DishFlavor> flavors = dishDTO.getFlavors();

    if (!Collections.isEmpty(flavors)) {
      /* 设置 dishId, 主键已经自动生成 */
      flavors.forEach(flavor -> flavor.setDishId(dish.getId()));
      if (!dishFlavorService.saveBatch(flavors)) {
        log.warn("save dishFlavor failed");
      }
    }

    String key = KeyUtil.wrapKey("DISH_VO", dishDTO.getCategoryId());
    redisTemplate.delete(key);
    log.info("Evict Cache {}", key);

    return row > 0;
  }

  @Override
  public PageResult findPage(DishPageDto dishPageDto) {
    PageHelper.startPage(Math.toIntExact(dishPageDto.getPage()), Math.toIntExact(dishPageDto.getPageSize()));

    List<DishVo> dishVoList = dishMapper.findPage(dishPageDto);


    Page<DishVo> page = (Page<DishVo>) dishVoList;
    return PageResult.getPageResult(page.getTotal(), page.getResult());
  }

  /**
   * 起售/停售菜品
   *
   * @param id     菜品ID
   * @param status 菜品状态
   * @return 结果
   */
  @Override
  public boolean updateStatusById(Long id, Integer status) {
    LocalDateTime now = LocalDateTime.now();
    Long currentUser = ThreadLocalContext.get();

    int row = dishMapper.updateStatusById(id, status, now, currentUser);
    Set<Object> keys = redisTemplate.keys("DISH_VO*");
    redisTemplate.delete(keys);
    log.info("Evict Cache {}", keys);

    return row == 1;
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
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

    Set<Object> keys = redisTemplate.keys("DISH_VO*");
    redisTemplate.delete(keys);
    log.info("Evict Cache {}", keys);

    return row > 0 && flag;
  }

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @Override
  public DishVo findById(Long id) {
    ValueOperations<Object, Object> cache = redisTemplate.opsForValue();
    String key = KeyUtil.wrapKey("DISH_VO", id);
    DishVo dishVo = (DishVo) cache.get(key);
    if (ObjectUtils.isNotEmpty(dishVo)) {
      log.info("Hit Cache {}", key);
      return dishVo;
    }

    log.info("Not Hit Cache {}", key);

    Dish dish = dishMapper.findById(id);
    dishVo = new DishVo();
    BeanUtils.copyProperties(dish, dishVo);
    Category category = categoryService.findById(dishVo.getCategoryId());
    List<DishFlavor> dishFlavors = dishFlavorService.findByDishId(id);
    dishVo.setCategoryName(category.getName());
    dishVo.setFlavors(dishFlavors);

    cache.set(key, dishVo);
    log.info("Set Cache {}", key);

    return dishVo;
  }

  /**
   * update dish info by its ID.
   *
   * @param dishDTO
   * @return
   */
  @Override
  public boolean updateById(DishDTO dishDTO) {
    int row = dishMapper.updateById(dishDTO);

    Long dishId = dishDTO.getId();
    dishFlavorService.deleteByDishId(dishId);

    List<DishFlavor> flavors = dishDTO.getFlavors();
    flavors.forEach(f -> f.setDishId(dishId));

    dishFlavorService.saveBatch(dishDTO.getFlavors());

    String key = KeyUtil.wrapKey("DISH_VO");
    redisTemplate.delete(key);
    log.info("Set Cache {}", key);

    return row > 0;
  }

  /**
   * @param categoryId
   * @return
   */
  @Override
  public List<DishVo> findByCategoryId(Long categoryId) {
    ValueOperations<Object, Object> cache = redisTemplate.opsForValue();
    String key = KeyUtil.wrapKey("DISH_VO", categoryId);
    List<DishVo> dishVoList = (List<DishVo>) cache.get(key);

    if (!Collections.isEmpty(dishVoList)) {
      log.info("Hit Cache {}", key);
      return dishVoList;
    }

    log.info("Not Hit Cache {}", key);

    Dish dish = new Dish();
    dish.setCategoryId(categoryId);
    dish.setStatus(1);
    dishVoList = dishMapper.findByCategoryId(dish);

    cache.set(key, dishVoList);
    log.info("Set Cache {}", key);

    return dishVoList;

  }
}
