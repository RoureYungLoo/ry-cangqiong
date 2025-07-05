package com.luruoyang.enums;

public enum StatusEnum {
  ENABLE(1),
  DISABLE(0);
  private Integer value;

  StatusEnum(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
