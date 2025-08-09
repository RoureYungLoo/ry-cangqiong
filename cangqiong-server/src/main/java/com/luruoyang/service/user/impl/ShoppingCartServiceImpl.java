package com.luruoyang.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.luruoyang.context.ThreadLocalContext;
import com.luruoyang.dto.user.ShoppingCartDto;
import com.luruoyang.entity.ShoppingCart;
import com.luruoyang.exception.BusinessException;
import com.luruoyang.mapper.user.ShoppingCartMapper;
import com.luruoyang.service.admin.DishService;
import com.luruoyang.service.admin.SetmealService;
import com.luruoyang.service.user.ShoppingCartService;
import com.luruoyang.vo.DishVo;
import com.luruoyang.vo.SetmealVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

  @Autowired
  private ShoppingCartMapper shoppingCartMapper;

  @Autowired
  private DishService dishService;

  @Autowired
  private SetmealService setmealService;

  @Override
  public boolean addShoppingCart(ShoppingCartDto dto) {
    // 菜品ID
    Long dishId = dto.getDishId();
    // 套餐ID
    Long setmealId = dto.getSetmealId();
    // 口味
    String dishFlavor = dto.getDishFlavor();

    ShoppingCart shoppingCart = BeanUtil.toBean(dto, ShoppingCart.class);
    shoppingCart.setUserId(ThreadLocalContext.get());
    ShoppingCart cartDb = shoppingCartMapper.findOne(shoppingCart);

    // 购物车里没有该 菜品/套餐
    if (Objects.isNull(cartDb)) {
      shoppingCart.setNumber(0);
      cartDb = shoppingCart;
      // 加购菜品
      if (Objects.nonNull(dishId) && Objects.isNull(setmealId)) {
        DishVo dish = dishService.findById(dishId);
        cartDb.setName(dish.getName());
        cartDb.setImage(dish.getImage());
        cartDb.setDishFlavor(dishFlavor);
        cartDb.setAmount(dish.getPrice());
      }

      // 加购套餐
      if (Objects.nonNull(setmealId) && Objects.isNull(dishId)) {
        SetmealVo setmeal = setmealService.findById(setmealId);
        cartDb.setName(setmeal.getName());
        cartDb.setImage(setmeal.getImage());
        cartDb.setAmount(setmeal.getPrice());
      }

      cartDb.setNumber(cartDb.getNumber() + 1);
      shoppingCartMapper.save(cartDb);
    } else {
      // 购物车里有该 菜品/套餐
      cartDb.setNumber(cartDb.getNumber() + 1);
      shoppingCartMapper.updateById(cartDb);
    }

    return true;
  }

  /**
   * 查询购物车List
   *
   * @param dto dto
   * @return list
   */
  @Override
  public List<ShoppingCart> list(ShoppingCartDto dto) {
    ShoppingCart shoppingCart = BeanUtil.toBean(dto, ShoppingCart.class);
    Long userId = ThreadLocalContext.get();

    if (ObjectUtil.isNull(userId)) {
      throw new BusinessException("用户ID获取异常");
    }

    shoppingCart.setUserId(userId);
    List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
    return list;
  }

  /**
   * 购物车物品减1
   *
   * @param dto dto
   * @return true or false
   */
  @Override
  public boolean subShoppingCart(ShoppingCartDto dto) {
    Long setmealId = dto.getSetmealId();
    Long dishId = dto.getDishId();

    if (Objects.isNull(setmealId) && Objects.isNull(dishId)) {
      throw new BusinessException("更新购物车 - 参数缺失");
    }

    ShoppingCart shoppingCart = BeanUtil.toBean(dto, ShoppingCart.class);
    shoppingCart.setUserId(ThreadLocalContext.get());
    ShoppingCart cart = shoppingCartMapper.findOne(shoppingCart);
    if (Objects.nonNull(cart)) {
      cart.setNumber(cart.getNumber() - 1);
      if (cart.getNumber() <= -1) {
        throw new BusinessException("购物车数据异常");
      }
      shoppingCartMapper.updateById(cart);
    }
    return true;
  }

  /**
   * 清空当前用户的购物车
   *
   * @return true of false
   */
  @Override
  public boolean cleanShoppingCart() {
    Long userId = ThreadLocalContext.get();
    shoppingCartMapper.deleteByUserIds(userId);
    return true;
  }
}
