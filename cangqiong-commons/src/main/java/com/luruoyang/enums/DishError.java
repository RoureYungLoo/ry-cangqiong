package com.luruoyang.enums;

public enum DishError {
  ERR_DISH_ONSALE("菜品起售中"), ERR_DISH_IN_SETMAEL("菜品关联套餐中");

  DishError(String errMsg) {
    this.errMsg = errMsg;
  }

  private String errMsg;

  public String getErrMsg() {
    return errMsg;
  }

  @Override
  public String toString() {
    return errMsg;
  }
}
