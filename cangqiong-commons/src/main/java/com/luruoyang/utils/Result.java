package com.luruoyang.utils;

import com.luruoyang.enums.ResCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Result {
  private Integer code;
  private String msg;
  private Object data;

  private Result() {
  }

  private Result(ResCode code, String msg, Object data) {
    this.code = code.getCode();
    this.msg = msg;
    this.data = data;
  }

  public static Result success() {
    return new Result(ResCode.SUCCESS, "success", null);
  }

  public static Result success(Object data) {
    Result result = new Result(ResCode.SUCCESS, "success", data);
    return result;
  }

  public static Result success(Integer code, Object data) {
    Result result = new Result(code, "success", data);
    return result;
  }

  public static Result fail(String msg) {
    return new Result(ResCode.FAIL, msg, null);

  }

  public static Result fail() {
    return new Result(ResCode.FAIL, "failed", "");
  }
}
