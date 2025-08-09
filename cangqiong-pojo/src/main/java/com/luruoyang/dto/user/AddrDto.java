package com.luruoyang.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AddrDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
}
