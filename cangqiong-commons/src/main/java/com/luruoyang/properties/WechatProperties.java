package com.luruoyang.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "cangqiong.wechat")
public class WechatProperties {
  private String appid;
  private String secret;
  private String authUrl;
}
