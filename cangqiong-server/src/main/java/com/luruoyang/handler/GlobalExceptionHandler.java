package com.luruoyang.handler;

import com.luruoyang.exception.BusinessException;
import com.luruoyang.utils.Result;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Result> BusinessException(BusinessException be) {
    log.warn("全局异常处理器捕获到BusinessException异常: {}", be.getMessage());
    be.printStackTrace();
    return ResponseEntity.ok(Result.fail(be.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Result> Exception(Exception e) {
    log.warn("全局异常处理器捕获到Exception异常: {}", e.getMessage());
    e.printStackTrace();

    return ResponseEntity.ok(Result.fail(e.getMessage()));
  }
}
