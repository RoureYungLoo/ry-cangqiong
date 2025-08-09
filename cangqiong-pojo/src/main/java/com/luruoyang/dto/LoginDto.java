package com.luruoyang.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "登录信息")
public class LoginDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  @Schema(description = "用户名",type = "String",required = true)
  private String username;
  @Schema(description = "密码",type = "String",required = true)
  private String password;
}
