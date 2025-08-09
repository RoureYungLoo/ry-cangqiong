package com.luruoyang.mapper.user;

import com.luruoyang.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressbookMapper {
  int save(AddressBook addressBook);

  List<AddressBook> findAllByUserId(AddressBook addressBook);

  int updateById(AddressBook addressBook);

  int deleteById(Long id);

  AddressBook findById(Long id);
}
