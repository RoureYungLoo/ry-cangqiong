package com.luruoyang.mapper.admin;

import com.luruoyang.annotation.AutoFill;
import com.luruoyang.dto.DishDTO;
import com.luruoyang.dto.DishPageDto;
import com.luruoyang.entity.Dish;
import com.luruoyang.enums.OpType;
import com.luruoyang.vo.DishVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DishMapper {
  @AutoFill(OpType.INSERT)
  int saveDish(Dish dish);

  List<DishVo> findPage(DishPageDto dishPageDto);


  int updateStatusById(Long id, Integer status, LocalDateTime now, Long currentUser);

  int deleteBatch(List<Long> ids);

  Dish findById(Long id);

  @AutoFill(OpType.UPDATE)
  int updateById(DishDTO dishDTO);

  Long findCount(@Param("ids") List<Long> ids, @Param("status") int status);

  List<DishVo> findByCategoryId(@Param("dish") Dish dish);
}
