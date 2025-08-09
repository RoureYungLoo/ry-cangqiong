package com.luruoyang.service.impl;

import com.luruoyang.entity.Merchant;
import com.luruoyang.entity.MerchantDto;
import com.luruoyang.mapper.MerchantMapper;
import com.luruoyang.service.MerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

  @Autowired
  private MerchantMapper merchantMapper;

  @Override
  @Cacheable(cacheNames = "merchant",key = "#merId")
  public Merchant findById(Long merId) {

    Merchant merchant = merchantMapper.findById(merId);

    return merchant;
  }

  @Override
  public List<Merchant> findByIds(List<Long> merIds) {

    List<Merchant> merchantList = merchantMapper.findByIds(merIds);

    return merchantList;
  }

  @Override
  public boolean save(MerchantDto merchantDto) {

    Merchant merchant = new Merchant();

    BeanUtils.copyProperties(merchantDto, merchant);

    int row = merchantMapper.save(merchant);


    return row > 0;
  }

  @Override
  public boolean deleteById(Long merchantId) {

    int row = merchantMapper.deleteById(merchantId);
    return row > 0;
  }

  @Override
  public boolean updateById(MerchantDto merchantDto) {

    Merchant merchant = new Merchant();
    BeanUtils.copyProperties(merchantDto, merchant);

    int row = merchantMapper.updateById(merchant);

    return row > 0;
  }
}
