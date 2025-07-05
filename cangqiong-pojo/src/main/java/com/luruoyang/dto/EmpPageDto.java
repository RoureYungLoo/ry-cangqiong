package com.luruoyang.dto;

import lombok.Data;

@Data
public class EmpPageDto {
  private Integer page;
  private Integer pageSize;
  private String name;
}
