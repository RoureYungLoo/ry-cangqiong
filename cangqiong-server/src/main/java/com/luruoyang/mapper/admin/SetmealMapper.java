package com.luruoyang.mapper.admin;

import com.luruoyang.entity.Setmeal;
import com.luruoyang.enums.StatusEnum;
import com.luruoyang.vo.SetmealVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealMapper {
  int save(Setmeal setmeal);

  List<SetmealVo> findPage(Setmeal setmeal);

  int deleteBatch(List<Long> ids);

  int findCountOnSale(@Param("ids") List<Long> ids, @Param("status") Integer status);

  SetmealVo findById(Long setmealId);

  int updateById(Setmeal setmeal);

  int updateStatusById(Setmeal setmeal);

  /* 根据SetmealID查询在售/停售的菜品数量 */
  int findCountByDishStatus(@Param("setmealId") Long setmealId,@Param("dishStatus") Integer dishStatus);
}
