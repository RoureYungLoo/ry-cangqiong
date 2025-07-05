package com.luruoyang.enums;

public enum Default {
  PWD("e10adc3949ba59abbe56e057f20f883e")
  ;
  private String pwd;

  Default(String pwd) {
    this.pwd = pwd;
  }

  public String getPwd() {
    return pwd;
  }
}
