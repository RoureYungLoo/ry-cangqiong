package com.luruoyang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "员工实体类")
public class Employ implements Serializable {
  private static final long serialVersionUID = 1L;
  @Schema(description = "主键,自增")
  private Long id;
  @Schema(description = "姓名")
  private String name;
  @Schema(description = "用户名")
  private String username;
  @Schema(description = "密码")
  private String password;
  @Schema(description = "手机号")
  private String phone;
  @Schema(description = "性别")
  private String sex;
  @Schema(description = "身份证号")
  private String idNumber;
  @Schema(description = "账号状态,1正常0锁定")
  private Integer status;
  @Schema(description = "创建时间")
//  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @Schema(description = "修改时间")
//  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;
  @Schema(description = "创建人id")
  private Long createUser;
  @Schema(description = "修改人id")
  private Long updateUser;
}
