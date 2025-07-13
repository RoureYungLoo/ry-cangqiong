package com.luruoyang.service.user.impl;

import com.luruoyang.context.ThreadLocalContext;
import com.luruoyang.dto.user.AddrDto;
import com.luruoyang.entity.AddressBook;
import com.luruoyang.mapper.user.AddressbookMapper;
import com.luruoyang.service.user.AddressBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

  @Autowired
  private AddressbookMapper addressbookMapper;

  @Override
  public boolean save(AddressBook addressBook) {
    int row = addressbookMapper.save(addressBook);
    return row > 0;
  }

  @Override
  public List<AddressBook> findAllByCurUserId() {
    Long curUserId = ThreadLocalContext.get();
    List<AddressBook> addressBookList = addressbookMapper.findAllByUserId(curUserId, null);

    return addressBookList;
  }

  @Override
  public AddressBook findDefaultAddress() {
    List<AddressBook> addressBookList = addressbookMapper.findAllByUserId(ThreadLocalContext.get(), (short) 1);

    return addressBookList.get(0);
  }

  @Override
  public boolean updateById(AddressBook addressBook) {
    int row = addressbookMapper.updateById(addressBook);

    return row > 0;
  }

  @Override
  public boolean deleteById(Long id) {
    int row = addressbookMapper.deleteById(id);
    return row > 0;
  }

  @Override
  public AddressBook findById(Long id) {
    return addressbookMapper.findById(id);
  }

  @Override
  public boolean updateAddrStatus(AddrDto addrDto) {
    AddressBook addressBook = new AddressBook();
    BeanUtils.copyProperties(addrDto, addressBook);
    addressBook.setIsDefault((short) 1);

    int updated = addressbookMapper.updateById(addressBook);
    return updated > 0;
  }
}
