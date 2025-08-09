package com.luruoyang.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private String code;
  private String location;
}
