package com.luruoyang.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class WechatAuthDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
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
