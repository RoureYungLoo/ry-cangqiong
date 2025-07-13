package com.luruoyang.service.user;

import com.luruoyang.dto.user.AddrDto;
import com.luruoyang.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
  boolean save(AddressBook addressBook);

  List<AddressBook> findAllByCurUserId();


  AddressBook findDefaultAddress();

  boolean updateById(AddressBook addressBook);

  boolean deleteById(Long id);

  AddressBook findById(Long id);

  boolean updateAddrStatus(AddrDto addrDto);
}
