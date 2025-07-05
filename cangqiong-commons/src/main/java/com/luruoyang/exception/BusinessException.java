package com.luruoyang.exception;

import com.luruoyang.enums.EmpError;

public class BusinessException extends RuntimeException {
  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(EmpError empError) {
    super(empError.getErrMsg());
  }

}
