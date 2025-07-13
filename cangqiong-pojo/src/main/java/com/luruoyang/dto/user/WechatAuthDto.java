package com.luruoyang.dto.user;

import lombok.Data;

@Data
public class WechatAuthDto {
  private String appid;
  private String secret;
  private String js_code;
  private String grant_type;
  private String session_key;
  private String unionid;
  private String errmsg;
  private String openid;
  private Integer errcode;
}
