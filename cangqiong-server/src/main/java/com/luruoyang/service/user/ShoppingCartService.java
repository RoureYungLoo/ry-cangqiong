package com.luruoyang.service.user;


import com.luruoyang.dto.user.ShoppingCartDto;
import com.luruoyang.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
  /**
   * 购物车物品加1
   *
   * @param dto dto
   * @return true or false
   */
  boolean addShoppingCart(ShoppingCartDto dto);

  /**
   * 查询购物车List
   *
   * @param dto dto
   * @return list
   */
  List<ShoppingCart> list(ShoppingCartDto dto);

  /**
   * 购物车物品减1
   *
   * @param dto dto
   * @return true or false
   */
  boolean subShoppingCart(ShoppingCartDto dto);

  /**
   * 清空当前用户的购物车
   *
   * @return true of false
   */
  boolean cleanShoppingCart();
}
