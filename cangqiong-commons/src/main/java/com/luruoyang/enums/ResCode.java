package com.luruoyang.enums;

public enum ResCode {
  SUCCESS(1), FAIL(0);

  ResCode(Integer code) {
    this.code = code;
  }

  private Integer code;

  public Integer getCode() {
    return code;
  }
}
