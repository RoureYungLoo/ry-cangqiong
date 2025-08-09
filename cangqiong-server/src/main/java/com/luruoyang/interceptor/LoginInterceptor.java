package com.luruoyang.interceptor;

import com.luruoyang.utils.JJwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author luruoyang
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Autowired
  private JJwtUtils jwtUtils;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getHeader("Token");

    if (jwtUtils.check(token,null)) {
      return true;
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }

  }
}
