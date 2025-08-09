package com.luruoyang.interceptor;

import com.luruoyang.properties.JJwtProperties;
import com.luruoyang.utils.JJwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class UserLoginInterceptor implements HandlerInterceptor {

  @Autowired
  private JJwtUtils jwtUtils;

  @Autowired
  private JJwtProperties jwtProperties;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getHeader(jwtProperties.getUserTokenName());

    // log.info("UserLoginInterceptor#preHandle:{}", token);

    if (jwtUtils.check(token, jwtProperties.getUserSecretKey())) {
      return true;
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }

  }
}
