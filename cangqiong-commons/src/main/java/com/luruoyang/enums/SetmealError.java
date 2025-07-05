package com.luruoyang.enums;

import lombok.Data;


public enum SetmealError {
  ENABLE_SETMEAL_WITH_DISABLED_DISH("起售套餐中不能包含已停售菜品"),
  ;

  SetmealError(String errMsg) {
    this.errMsg = errMsg;
  }


  public String getErrMsg() {
    return errMsg;
  }

  private String errMsg;
}
