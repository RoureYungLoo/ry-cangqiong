package com.luruoyang.config;

import com.github.xiaoymin.knife4j.core.model.OpenAPIInfo;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPIInfo() {
    return new OpenAPI()

        .info(new Info()

            .title("外卖管理系统")
            .contact(new Contact().url("http://contact.me").name("禄若阳").email("694306515@qq.com"))
            .description("一个外卖管理系统, 可供商家、骑手、用户使用")
            .summary("API文档总结： 略")
            .termsOfService("http://termsOfService.com")
            .version("1.0.0")
            .license(new License().name("许可证").url("http://license.com"))
        );

  }
}
