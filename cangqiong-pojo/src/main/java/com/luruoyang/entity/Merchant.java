package com.luruoyang.entity;

import lombok.Data;

@Data
public class Merchant {
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
