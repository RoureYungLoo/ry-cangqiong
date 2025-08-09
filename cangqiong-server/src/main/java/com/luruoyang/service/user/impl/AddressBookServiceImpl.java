package com.luruoyang.service.user.impl;

import com.luruoyang.constant.Constants;
import com.luruoyang.context.ThreadLocalContext;
import com.luruoyang.dto.AddressBookDto;
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

  /**
   * 添加地址
   *
   * @param addressBook
   * @return
   */
  @Override
  public boolean save(AddressBook addressBook) {
    Long userId = ThreadLocalContext.get();
    addressBook.setUserId(userId);
    addressBook.setIsDefault(Constants.NOT_DEFAULT);
    int row = addressbookMapper.save(addressBook);
    return row > 0;
  }

  /**
   * 查询当前登录用户的所有地址信息
   *
   * @return
   */
  @Override
  public List<AddressBook> findAllByCurUserId() {
    Long userId = ThreadLocalContext.get();
    AddressBook addressBook = AddressBook.builder().userId(userId).build();
    List<AddressBook> addressBookList = addressbookMapper.findAllByUserId(addressBook);
    return addressBookList;
  }

  /**
   * 查询默认地址
   *
   * @return
   */
  @Override
  public AddressBook findDefaultAddress() {
    Long userId = ThreadLocalContext.get();
    AddressBook addressBook = AddressBook.builder().userId(userId).isDefault(Constants.DEFAULT).build();
    List<AddressBook> addressBookList = addressbookMapper.findAllByUserId(addressBook);
    return addressBookList.get(0);
  }

  /**
   * 根据ID修改地址
   *
   * @param addressBook
   * @return
   */
  @Override
  public boolean updateById(AddressBook addressBook) {
    int row = addressbookMapper.updateById(addressBook);
    return row > 0;
  }

  /**
   * 根据ID删除地址
   *
   * @param id
   * @return
   */
  @Override
  public boolean deleteById(Long id) {
    int row = addressbookMapper.deleteById(id);
    return row > 0;
  }

  /**
   * 根据ID查询地址
   *
   * @param id
   * @return
   */
  @Override
  public AddressBook findById(Long id) {
    return addressbookMapper.findById(id);
  }

  /**
   * 设置默认地址
   *
   * @param dto
   * @return
   */
  @Override
  public boolean updateAddrStatus(AddressBookDto dto) {
    Long userId = ThreadLocalContext.get();
    dto.setUserId(userId);

    dto.setIsDefault(Constants.DEFAULT);
    List<AddressBook> addrDbList = addressbookMapper.findAllByUserId(dto);
    addrDbList.forEach(item -> {
      item.setIsDefault(Constants.NOT_DEFAULT);
      addressbookMapper.updateById(item);
    });

    dto.setIsDefault(Constants.DEFAULT);
    int updated = addressbookMapper.updateById(dto);
    return updated > 0;
  }
}
