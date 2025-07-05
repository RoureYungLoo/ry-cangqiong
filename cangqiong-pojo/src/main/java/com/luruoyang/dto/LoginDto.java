package com.luruoyang.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录信息")
public class LoginDto {
  @Schema(description = "用户名",type = "String",required = true)
  private String username;
  @Schema(description = "密码",type = "String",required = true)
  private String password;
}
