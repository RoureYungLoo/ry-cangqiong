package com.luruoyang.interceptor;

import com.luruoyang.properties.JJwtProperties;
import com.luruoyang.utils.JJwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

  @Autowired
  private JJwtUtils jwtUtils;

  @Autowired
  private JJwtProperties jwtProperties;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getHeader(jwtProperties.getAdminTokenName());

    if (jwtUtils.check(token, jwtProperties.getAdminSecret())) {
      return true;
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }

  }
}
