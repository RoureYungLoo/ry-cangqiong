package com.luruoyang.config;

import com.luruoyang.interceptor.AdminLoginInterceptor;
import com.luruoyang.interceptor.UserLoginInterceptor;
import com.luruoyang.json.JacksonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private AdminLoginInterceptor adminLoginInterceptor;

  @Autowired
  private UserLoginInterceptor userLoginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    String[] excludePatterns = new String[]{
        "/admin/employee/login",
        "/user/user/login",
        "/user/shop/status",
        "/webjars/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/swagger-ui.html/**",
        "/swagger-ui/**",
        "/api",
        "/api-docs",
        "/api-docs/**",
        "/doc.html/**"};
    registry.addInterceptor(adminLoginInterceptor)
        .addPathPatterns("/admin/**")
        .excludePathPatterns(excludePatterns);

    registry.addInterceptor(userLoginInterceptor)
        .addPathPatterns("/user/**")
        .excludePathPatterns(excludePatterns);
  }


  /* 日期格式转换 */
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(new JacksonObjectMapper());
    converters.add(6, converter); // 0改为6, 解决 knif4j : Unexpected token 'e', "eyJvcGVuYX"... is not valid JSON
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/doc.html").
        addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").
        addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
