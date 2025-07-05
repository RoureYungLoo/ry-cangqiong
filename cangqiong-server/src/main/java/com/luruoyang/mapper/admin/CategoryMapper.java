package com.luruoyang.mapper.admin;

import com.luruoyang.annotation.AutoFill;
import com.luruoyang.dto.CategoryDto;
import com.luruoyang.entity.Category;
import com.luruoyang.enums.OpType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
  @AutoFill(OpType.INSERT)
  int save(Category category);

  List<Category> findPage(CategoryDto categoryDto);

  int deleteById(Long categoryId);

  Category findById(Long id);

  @AutoFill(OpType.UPDATE)
  int updateById(Category category);

  List<Category> list(Integer type);
}
