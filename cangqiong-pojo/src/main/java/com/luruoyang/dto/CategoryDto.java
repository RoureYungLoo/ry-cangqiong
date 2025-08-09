package com.luruoyang.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(title = "分类Dto")
public class CategoryDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  @Schema(title = "主键ID")
  private Long id;
  private String name;
  private Integer type;
  private Integer sort;
  private Integer status;
  private Long page;
  private Long pageSize;
}
