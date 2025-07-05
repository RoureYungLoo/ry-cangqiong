package com.luruoyang.dto;

import com.luruoyang.entity.Setmeal;
import com.luruoyang.entity.SetmealDish;
import lombok.Data;

import java.util.List;


@Data
public class SetmealDto extends Setmeal {

  private List<SetmealDish> setmealDishes;

  private Integer page;
  private Integer pageSize;

}
