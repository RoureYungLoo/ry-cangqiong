package com.luruoyang.aspect;

import com.luruoyang.annotation.AutoFill;
import com.luruoyang.context.ThreadLocalContext;
import com.luruoyang.enums.OpType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class AutoFillAspect {

  @Pointcut("execution(* com.luruoyang.mapper..*.*(..)) && @annotation(com.luruoyang.annotation.AutoFill)")
  public void autoFillPointCut() {

  }

  @Before("autoFillPointCut()")
  public void autoFill(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();

    MethodSignature methodSignature = (MethodSignature) signature;

    AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);

    OpType opType = autoFill.value();

    Object[] args = joinPoint.getArgs();
    if (Objects.isNull(args) || args.length == 0) return;

    Object arg = args[0];

    try {
      fill(opType, arg);
    } catch (Exception e) {
      log.error("autoFill {} failed: {}", methodSignature, e.getMessage());
      throw new RuntimeException(e);
    }

  }

  private static void fill(OpType opType, Object arg) throws Exception {
    LocalDateTime now = LocalDateTime.now();
    Long opUserId = ThreadLocalContext.get();
    Method setCreateTime = arg.getClass().getMethod("setCreateTime", LocalDateTime.class);
    Method setUpdateTime = arg.getClass().getMethod("setUpdateTime", LocalDateTime.class);
    Method setCreateUser = arg.getClass().getMethod("setCreateUser", Long.class);
    Method setUpdateUser = arg.getClass().getMethod("setUpdateUser", Long.class);
    switch (opType) {
      case UPDATE:
        setUpdateTime.invoke(arg, now);
        setUpdateUser.invoke(arg, opUserId);
        break;
      case INSERT:
        setCreateTime.invoke(arg, now);
        setUpdateTime.invoke(arg, now);
        setCreateUser.invoke(arg, opUserId);
        setUpdateUser.invoke(arg, opUserId);
        break;
    }
  }
}
