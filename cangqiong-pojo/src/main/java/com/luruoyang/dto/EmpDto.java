package com.luruoyang.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "新增员工dto")
public class EmpDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  @NotNull
  @Schema(description = "员工id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private Long id;
  @Schema(description = "身份证", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String idNumber;
  @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String name;
  @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String phone;
  @Schema(description = "性别", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sex;
  @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String username;
}
