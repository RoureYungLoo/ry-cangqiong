package com.luruoyang.vo;

import com.luruoyang.entity.Setmeal;
import com.luruoyang.entity.SetmealDish;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SetmealVo extends Setmeal {
  private String categoryName;
  private List<SetmealDish> setmealDishes;
}
