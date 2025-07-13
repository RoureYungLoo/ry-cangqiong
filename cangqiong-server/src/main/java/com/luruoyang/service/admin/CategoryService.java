package com.luruoyang.service.admin;

import com.luruoyang.dto.CategoryDto;
import com.luruoyang.entity.Category;
import com.luruoyang.utils.PageResult;

import java.util.List;

public interface CategoryService {
  boolean save(CategoryDto categoryDto);

  PageResult findPage(CategoryDto categoryDto);

  boolean deleteById(Long id);

  boolean updateById(CategoryDto categoryDto);

  boolean updateStatusById(Long id, Integer status);

  List<Category> list(Integer type, Integer status);

  Category findById(Long categoryId);
}
