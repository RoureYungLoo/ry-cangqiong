package com.luruoyang.service;

import com.luruoyang.entity.Merchant;
import com.luruoyang.entity.MerchantDto;

import java.util.List;

public interface MerchantService {
  Merchant findById(Long merId);

  List<Merchant> findByIds(List<Long> merIds);

  boolean save(MerchantDto merchantDto);

  boolean deleteById(Long merchantId);

  boolean updateById(MerchantDto merchantDto);


}
