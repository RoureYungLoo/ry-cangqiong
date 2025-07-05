package com.luruoyang.context;

/* 线程本地存储 */
public class ThreadLocalContext {
  private static ThreadLocal<Long> tl = new ThreadLocal<>();

  public static Long get() {
    return tl.get();
  }

  public static void set(Long userId) {
    tl.set(userId);
  }

  public static void rm() {
    tl.remove();
  }
}
