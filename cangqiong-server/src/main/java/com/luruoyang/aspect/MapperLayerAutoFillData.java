package com.luruoyang.aspect;

import com.luruoyang.context.ThreadLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Aspect
@Component
public class MapperLayerAutoFillData {

  @Pointcut("execution(* com.luruoyang.mapper..*.save*(*))")
  public void save() {
  }

  @Pointcut("execution(public * com.luruoyang.mapper..*.update*(*))")
  public void update() {
  }

  @Around("save() || update()")
  public Object aroundAdviceSave(ProceedingJoinPoint pjp) throws Throwable {
    Object returnValue = null;
    try {
      Object[] args = pjp.getArgs();
      Object arg = args[0];
      log.warn("-------> arg:{}", arg);
      LocalDateTime now = LocalDateTime.now();
      Long currentUser = ThreadLocalContext.get();
      Class<?> clazz = arg.getClass();

      Method setCreateTime;
      Method setUpdateTime;
      Method setCreateUser;
      Method setUpdateUser;

      try {
        PropertyDescriptor createTime = new PropertyDescriptor("createTime", clazz);
        setCreateTime = createTime.getWriteMethod();
        setCreateTime.invoke(arg, now);
      } catch (Exception e) {
        log.warn(" [warn]: {}", e.getMessage());
      }

      try {
        PropertyDescriptor updateTime = new PropertyDescriptor("updateTime", clazz);
        setUpdateTime = updateTime.getWriteMethod();
        setUpdateTime.invoke(arg, now);
      } catch (Exception e) {
        log.warn(" [warn]: {}", e.getMessage());
      }

      try {
        PropertyDescriptor createUser = new PropertyDescriptor("createUser", clazz);
        setCreateUser = createUser.getWriteMethod();
        setCreateUser.invoke(arg, currentUser);
      } catch (Exception e) {
        log.warn(" [warn]: {}", e.getMessage());
      }

      try {
        PropertyDescriptor updateUser = new PropertyDescriptor("updateUser", clazz);
        setUpdateUser = updateUser.getWriteMethod();
        setUpdateUser.invoke(arg, currentUser);
      } catch (Exception e) {
        log.warn(" [warn]: {}", e.getMessage());
      }

      log.error("==============>>>>>>>>>>>>>>>>> 前置通知 ");
      returnValue = pjp.proceed();

    } catch (Throwable e) {
      e.printStackTrace();
      throw e;
    } finally {

    }

    return returnValue;
  }
}

/*
  @Around("update()")
  public Object aroundAdviceUpdate(ProceedingJoinPoint pjp) throws Throwable {

    Object returnValue = null;
    try {

      Object[] args = pjp.getArgs();
      Object arg = args[0];
      log.warn("-------> arg:{}", arg);
      Class<?> clazz = arg.getClass();
      Method setUpdateTime;
      Method setUpdateUser;
      try {
        PropertyDescriptor updateTime = new PropertyDescriptor("updateTime", clazz);
        PropertyDescriptor updateUser = new PropertyDescriptor("updateUser", clazz);

        setUpdateTime = updateTime.getWriteMethod();
        setUpdateUser = updateUser.getWriteMethod();

        LocalDateTime now = LocalDateTime.now();
        Long currentUser = ThreadLocalContext.get();
        setUpdateTime.invoke(arg, now);
        setUpdateUser.invoke(arg, currentUser);

      } catch (IntrospectionException | InvocationTargetException | IllegalArgumentException |
               IllegalAccessException e) {
        log.warn("------>  {}: {}", arg.getClass(), e.getMessage());
      }

      returnValue = pjp.proceed();

    } catch (Throwable e) {
      e.printStackTrace();
      throw e;
    }
    return returnValue;
  }
}*/
