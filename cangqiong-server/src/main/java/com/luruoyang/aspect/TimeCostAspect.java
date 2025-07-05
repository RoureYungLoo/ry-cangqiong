package com.luruoyang.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeCostAspect {

  @Pointcut("execution(public * com.luruoyang.controller.admin.*Controller.*(..))")
  public void timepointcut() {

  }

  @Around(value = "timepointcut()")
  public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.currentTimeMillis();
    Signature signature = pjp.getSignature();
    Class aClass = signature.getDeclaringType();
    String methodName = signature.getName();
    Object[] args = pjp.getArgs();

    Object returnValue = null;
    try {
      /* 前置通知 */
      returnValue = pjp.proceed();
      /* 返回后通知 */
      return returnValue;
    } catch (Throwable e) {
      /* 异常通知 */
      log.error("时间切面捕获到异常: {}", e);
      throw e;
    } finally {
      /* 最终通知 */
      long end = System.currentTimeMillis();
      log.info("{}#{}()执行耗时: {} ms, args: {}", aClass, methodName, end - start, args);
    }
  }
}
