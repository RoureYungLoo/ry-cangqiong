package com.luruoyang.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luruoyang.dto.SetmealDto;
import com.luruoyang.entity.Setmeal;
import com.luruoyang.entity.SetmealDish;
import com.luruoyang.enums.SaleStatus;
import com.luruoyang.enums.SetmealError;
import com.luruoyang.enums.StatusEnum;
import com.luruoyang.exception.BusinessException;
import com.luruoyang.mapper.admin.SetmealMapper;
import com.luruoyang.service.admin.SetmealDishService;
import com.luruoyang.service.admin.SetmealService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.vo.SetmealVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SetmealServiceImpl implements SetmealService {

  @Autowired
  private SetmealMapper setmealMapper;

  @Autowired
  private SetmealDishService setmealDishService;

  /**
   * save setMeal
   *
   * @param setmealDto dto
   * @return true or false
   */
  @CacheEvict(cacheNames = "setmealCache", key = "#setmealDto.categoryId")
  @Override
  public boolean save(SetmealDto setmealDto) {

    /* 保存 套餐表 */
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(setmealDto, setmeal);

    setmeal.setStatus(StatusEnum.DISABLE.ordinal());

    int row = setmealMapper.save(setmeal);
    /* 主键数据库自动生成 */
    Long setmealId = setmeal.getId();
    List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();

    setmealDishList.forEach(sd -> sd.setSetmealId(setmealId));

    /*保存 套餐-菜品 管理表*/

    row = setmealDishService.saveBatch(setmealDishList);

    return true;
  }

  @Override
  public PageResult findPage(SetmealDto setmealDto) {

    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(setmealDto, setmeal);

    PageHelper.startPage(setmealDto.getPage(), setmealDto.getPageSize());

    List<SetmealVo> seatmealVoList = setmealMapper.findPage(setmeal);

    Page<SetmealVo> page = (Page<SetmealVo>) seatmealVoList;
    return PageResult.getPageResult(page.getTotal(), page.getResult());
  }

  /**
   * delete setMeals by their ids
   *
   * @param ids ids
   * @return true or false
   */
  @CacheEvict(cacheNames = "setmealCache", allEntries = true)
  @Override
  public boolean deleteBatch(List<Long> ids) {

    if (ids.isEmpty()) {
      throw new BusinessException("批量删除ID不能为空");
    }

    int count = setmealMapper.findCountOnSale(ids, SaleStatus.ON_SALE.value());
    if (count > 0) {
      throw new BusinessException("起售中的套餐不能删除");
    }

    int row = setmealMapper.deleteBatch(ids);

    /* 图片删除 */

    /* Setmeal Dish 连接表也得删除 */
    row = setmealDishService.deleteSetmealDishBySetmealId(ids);


    return true;
  }

  /**
   * find setmeal by setmealId
   *
   * @param setmealId setmealId
   * @return SetmealVo
   */
  @Cacheable(cacheNames = "setmealCache",key = "#setmealId")
  @Override
  public SetmealVo findById(Long setmealId) {
    SetmealVo vo = setmealMapper.findById(setmealId);
    return vo;
  }

  /**
   * update setmeal
   *
   * @param setmealDto
   * @return
   */
  @CacheEvict(cacheNames = "setmealCache", allEntries = true)
  @Override
  public boolean updateById(SetmealDto setmealDto) {
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(setmealDto, setmeal);

    /* 更新Setmeal表 */
    int row = setmealMapper.updateById(setmeal);

    /* 删除旧的 */
    Long setmealId = setmeal.getId();
    List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
    setmealDishes.forEach(sd -> sd.setSetmealId(setmealId));

    row = setmealDishService.deleteSetmealDishBySetmealId(List.of(setmealId));
    /* 添加新的 */

    row = setmealDishService.saveBatch(setmealDishes);

    return true;
  }

  @CacheEvict(cacheNames = "setmealCache", allEntries = true)
  @Override
  public boolean updateStatusById(SetmealDto setmealDto) {
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(setmealDto, setmeal);

    /* 起售时套餐菜品不能包含停售菜品 */
    if (Objects.equals(setmealDto.getStatus(), StatusEnum.ENABLE.getValue())) {
      int count = setmealMapper.findCountByDishStatus(setmeal.getId(), SaleStatus.OFF_SALE.value());
      if (count > 0) {
        log.warn("[套餐模块告警] {}", SetmealError.ENABLE_SETMEAL_WITH_DISABLED_DISH.getErrMsg());
        throw new BusinessException("起售套餐中不能包含已停售菜品");
      }
    }

    /* 更新Setmeal表 */
    int row = setmealMapper.updateStatusById(setmeal);

    return row > 0;
  }

  /**
   * 根据分类id查询套餐
   *
   * @param categoryId 分类ID
   * @return List<Setmeal>
   */
  @Cacheable(cacheNames = "setmealCache::categoryId", key = "#categoryId")
  @Override
  public List<Setmeal> findByCategoryId(Long categoryId) {
    List<Setmeal> setmealList = setmealMapper.findByCategroyId(categoryId);
    return setmealList;
  }
}
