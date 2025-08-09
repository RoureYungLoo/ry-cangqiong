package com.luruoyang.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EmpPageDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private Integer page;
  private Integer pageSize;
  private String name;
}
