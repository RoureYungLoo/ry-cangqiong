package com.luruoyang.entity;

import lombok.Data;

@Data
public class AddressBook {
  private Long id;
  private Long userId;
  private String consignee;
  private String sex;
  private String phone;
  private String provinceCode;
  private String provinceName;
  private String cityCode;
  private String cityName;
  private String districtCode;
  private String districtName;
  private String detail;
  private String label;
  private Short isDefault;
}
