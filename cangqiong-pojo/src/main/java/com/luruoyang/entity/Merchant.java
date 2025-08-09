package com.luruoyang.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Merchant implements Serializable {
  private static final long serialVersionUID = 1L;
  protected Long id;
  protected String merchantNo;
  protected String shopName;
  protected String shopTelephone;
  protected String shopAddress;
  protected String deliveryTime;
  protected String merchantEvaluation;
  protected String merchantFoodsafetyArchives;
  protected String merchantProvidesDeliveryFees;
}
