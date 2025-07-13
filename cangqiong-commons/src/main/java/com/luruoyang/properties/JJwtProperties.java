package com.luruoyang.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "cangqiong.jjwt")
public class JJwtProperties {
  private String adminSecret;
  private String adminTokenName;
  private Long adminExpiration;

  private String userSecretKey;
  private String userTokenName;
  private Long userTtl;
}
