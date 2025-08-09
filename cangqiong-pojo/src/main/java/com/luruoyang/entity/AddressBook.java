package com.luruoyang.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(
    name = "AddressBook",
    description = "AddressBook",
    type = "AddressBook"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressBook implements Serializable {
  private static final long serialVersionUID = 1L;

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "用户id")
  private Long userId;

  @Schema(description = "收货人")
  private String consignee;

  @Schema(description = "性别")
  private String sex;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "省份编码")
  private String provinceCode;

  @Schema(description = "省份名称")
  private String provinceName;

  @Schema(description = "城市编码")
  private String cityCode;

  @Schema(description = "城市名称")
  private String cityName;

  @Schema(description = "区县编码")
  private String districtCode;

  @Schema(description = "区县名称")
  private String districtName;

  @Schema(description = "详细地址信息")
  private String detail;

  @Schema(description = "标签")
  private String label;

  @Schema(description = "是否默认地址")
  private Short isDefault;
}
