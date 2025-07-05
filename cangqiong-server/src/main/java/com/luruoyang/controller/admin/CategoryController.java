package com.luruoyang.controller.admin;

import com.luruoyang.dto.CategoryDto;
import com.luruoyang.entity.Category;
import com.luruoyang.service.admin.CategoryService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Tag(name = "分类管理")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @PostMapping
  @Operation(summary = "添加分类111")
  public ResponseEntity<Result> save(@RequestBody CategoryDto categoryDto) {
    if (categoryService.save(categoryDto)) {
      return ResponseEntity.ok(Result.success());
    } else {
      return ResponseEntity.ok(Result.fail());
    }
  }

  @Operation(summary = "条件分页查询")
  @GetMapping("/page")
  public Result page(CategoryDto categoryDto) {
    PageResult pageResult = categoryService.findPage(categoryDto);
    return Result.success(1, pageResult);
  }

  @Operation(summary = "根据ID删除分类")
  @DeleteMapping
  public Result deleteCategoryById(@RequestParam("id") Long id) {
    if (categoryService.deleteById(id)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @Operation(summary = "修改分类")
  @PutMapping
  public Result updateById(@RequestBody CategoryDto categoryDto) {
    if (categoryService.updateById(categoryDto)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @Operation(summary = "启用禁用分类")
  @PostMapping("/status/{status}")
  public Result updateStatus(@PathVariable("status") Integer status, @RequestParam("id") Long id) {
    if (categoryService.updateStatusById(id, status)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @GetMapping("/list")
  public Result list(@RequestParam("type") Integer type) {
    List<Category> categoryList = categoryService.list(type);
    return Result.success(categoryList);
  }

}
