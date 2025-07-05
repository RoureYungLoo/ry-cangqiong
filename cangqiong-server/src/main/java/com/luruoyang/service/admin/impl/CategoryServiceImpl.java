package com.luruoyang.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luruoyang.constant.RedisKey;
import com.luruoyang.dto.CategoryDto;
import com.luruoyang.entity.Category;
import com.luruoyang.mapper.admin.CategoryMapper;
import com.luruoyang.service.admin.CategoryService;
import com.luruoyang.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryMapper categoryMapper;
  @Autowired
  private RedisTemplate redisTemplate;

  @Override
  public boolean save(CategoryDto categoryDto) {
    Category category = new Category();
    BeanUtils.copyProperties(categoryDto, category);

    /*LocalDateTime now = LocalDateTime.now();
    Long curUserId = ThreadLocalContext.get();
    category.setCreateTime(now);
    category.setUpdateTime(now);
    category.setCreateUser(curUserId);
    category.setUpdateUser(curUserId);*/

    category.setStatus(0);

    /* 清空缓存 */
    redisTemplate.opsForValue().getAndDelete(RedisKey.CATEGORY_LIST + "::" + category.getType());

    int row = categoryMapper.save(category);
    return row == 1;
  }

  @Override
  public PageResult findPage(CategoryDto categoryDto) {

    PageHelper.startPage(Math.toIntExact(categoryDto.getPage()), Math.toIntExact(categoryDto.getPageSize()));
    List<Category> categoryList = categoryMapper.findPage(categoryDto);

    Page<Category> page = (Page<Category>) categoryList;
    return PageResult.getPageResult(page.getTotal(), page.getResult());
  }

  @Override
  public boolean deleteById(Long id) {
    int row = categoryMapper.deleteById(id);

    /* 清空缓存 */
    return row == 1;
  }

  @Override
  public boolean updateById(CategoryDto categoryDto) {
    Category categoryDB = categoryMapper.findById(categoryDto.getId());
    if (Objects.isNull(categoryDB)) return false;
    BeanUtils.copyProperties(categoryDto, categoryDB);

    /*categoryDB.setUpdateUser(ThreadLocalContext.get());
    categoryDB.setUpdateTime(LocalDateTime.now());*/

    int row = categoryMapper.updateById(categoryDB);

    /* 清空缓存 */

    return row == 1;
  }

  @Override
  public boolean updateStatusById(Long id, Integer status) {
    Category categoryDB = categoryMapper.findById(id);
    Objects.requireNonNull(categoryDB);

    categoryDB.setStatus(status);
    /*categoryDB.setUpdateTime(LocalDateTime.now());
    categoryDB.setUpdateUser(ThreadLocalContext.get());*/

    int row = categoryMapper.updateById(categoryDB);

    /* 清空缓存 */
    return row == 1;
  }

  @Override
  public List<Category> list(Integer type) {
    List<Category> categoryList;
    String key = RedisKey.CATEGORY_LIST + type;
    /* 添加缓存*/
    ValueOperations<String, List<Category>> redis = redisTemplate.opsForValue();
    categoryList = redis.get(key);

    if (categoryList == null || categoryList.isEmpty()) {
      log.warn("菜品分类 未命中缓存");
      categoryList = categoryMapper.list(type);
      redis.set(key, categoryList);
    } else {
      log.warn("菜品分类 命中缓存");
    }

    return categoryList;
  }

  @Override
  public Category findById(Long categoryId) {
    return categoryMapper.findById(categoryId);
  }

}
