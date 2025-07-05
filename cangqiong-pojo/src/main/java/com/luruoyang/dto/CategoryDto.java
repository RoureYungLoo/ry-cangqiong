package com.luruoyang.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "分类Dto")
public class CategoryDto {
  @Schema(title = "主键ID")
  private Long id;
  private String name;
  private Integer type;
  private Integer sort;
  private Integer status;
  private Long page;
  private Long pageSize;
}
