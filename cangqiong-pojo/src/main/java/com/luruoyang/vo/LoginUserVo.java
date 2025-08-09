package com.luruoyang.vo;

import com.luruoyang.entity.Merchant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String token;

  private String userName;
  private String name;

  private BigDecimal deliveryFee;
  @Schema(description = "小程序登录openid")
  private String openid;
  private String shopAddress;
  private Long shopId;
  private String shopTelephone;

  public void setMerchant(Merchant merchant) {
    this.shopAddress = merchant.getShopAddress();
    this.shopId = merchant.getId();
    this.shopTelephone = merchant.getShopTelephone();
  }
}
