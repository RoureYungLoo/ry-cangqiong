package com.luruoyang.dto;

import com.luruoyang.entity.Setmeal;
import com.luruoyang.entity.SetmealDish;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Data
public class SetmealDto extends Setmeal implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private List<SetmealDish> setmealDishes;

  private Integer page;
  private Integer pageSize;

}
