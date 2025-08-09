package com.luruoyang.mapper.user;

import com.luruoyang.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
  int save(ShoppingCart shoppingCart);

  int deleteById(Long id);

  int deleteByIds(Long[] id);

  int updateById(ShoppingCart shoppingCart);

  ShoppingCart findById(Long id);

  List<ShoppingCart> list(ShoppingCart shoppingCart);

  ShoppingCart findOne(ShoppingCart shoppingCart);

  int deleteByUserIds(Long... userIds);
}
