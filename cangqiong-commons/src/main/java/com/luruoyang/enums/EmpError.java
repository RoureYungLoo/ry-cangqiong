package com.luruoyang.enums;

public enum EmpError {
  USER_EXISTS("用户名已存在")
  ;
  private String errMsg;

  EmpError(String errMsg) {
    this.errMsg = errMsg;
  }

  public String getErrMsg() {
    return errMsg;
  }
}
