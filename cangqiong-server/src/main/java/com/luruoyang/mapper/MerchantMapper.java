package com.luruoyang.mapper;

import com.luruoyang.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MerchantMapper {
  Merchant findById(Long merId);

  List<Merchant> findByIds(List<Long> merIds);

  int save(Merchant merchant);

  int deleteById(Long merchantId);

  int updateById(Merchant merchant);
}
