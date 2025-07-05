package com.luruoyang.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {

  private Long total;
  private List records;

  public static PageResult getPageResult(Long total, List list) {
    return new PageResult(total, list);
  }
}
