package com.luruoyang.service.user;

import com.luruoyang.dto.AddressBookDto;
import com.luruoyang.dto.user.AddrDto;
import com.luruoyang.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
  /**
   * 添加地址
   *
   * @param addressBook
   * @return
   */
  boolean save(AddressBook addressBook);

  /**
   * 查询当前登录用户的所有地址信息
   * @return
   */
  List<AddressBook> findAllByCurUserId();

  /**
   * 查询默认地址
   * @return
   */
  AddressBook findDefaultAddress();

  /**
   * 根据ID修改地址
   * @param addressBook
   * @return
   */
  boolean updateById(AddressBook addressBook);

  /**
   * 根据ID删除地址
   * @param id
   * @return
   */
  boolean deleteById(Long id);

  /**
   * 根据ID查询地址
   * @param id
   * @return
   */
  AddressBook findById(Long id);

  /**
   * 设置默认地址
   * @param dto
   * @return
   */
  boolean updateAddrStatus(AddressBookDto dto);
}
