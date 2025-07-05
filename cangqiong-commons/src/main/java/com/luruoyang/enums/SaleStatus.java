package com.luruoyang.enums;

public enum SaleStatus {
  ON_SALE(1),
  OFF_SALE(0);

  private Integer value;

  SaleStatus(Integer status) {
    this.value = status;
  }

  public Integer value() {
    return value;
  }
}
