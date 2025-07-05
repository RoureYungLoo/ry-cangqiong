package com.luruoyang.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(title = "分类实体类")
public class Category {
  @Schema(title = "主键,自增")
  private Long id;
  @Schema(title = "分类名称,唯一不重复")
  private Integer type;
  @Schema(title = "分类类型, 1菜品,2 套餐")
  private String name;
  @Schema(title = "排序字段")
  private Integer sort;
  @Schema(title = "状态,1启用 0禁用")
  private Integer status = 1;
  @Schema(title = "创建时间")
  private LocalDateTime createTime;
  @Schema(title = "修改时间")
  private LocalDateTime updateTime;
  @Schema(title = "创建人id")
  private Long createUser;
  @Schema(title = "修改人id")
  private Long updateUser;
}
