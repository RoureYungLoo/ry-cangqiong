package com.luruoyang.utils;

import com.luruoyang.constant.RedisKey;

import java.util.Arrays;

/**
 * @author luruoyang
 */
public class KeyUtil {
  public static String wrapKey(Object module, Object... item) {
    StringBuilder sb = new StringBuilder();
    sb.append(module);
    for (Object s : item) {
      sb.append(RedisKey.SEPARATOR).append(s);
    }
    return sb.toString();
  }
}
