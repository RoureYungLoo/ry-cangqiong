package com.luruoyang.controller.user;

import com.luruoyang.dto.AddressBookDto;
import com.luruoyang.dto.user.AddrDto;
import com.luruoyang.entity.AddressBook;
import com.luruoyang.service.user.AddressBookService;
import com.luruoyang.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Tag(name = "地址管理")
public class AddressBookController {

  @Autowired
  private AddressBookService addressBookService;

  @PostMapping
  @Operation(summary = "添加地址")
  public Result saveAddress(@RequestBody AddressBook addressBook) {
    if (addressBookService.save(addressBook)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @GetMapping("/list")
  @Operation(summary = "查询当前登录用户的所有地址信息")
  public Result findAddressByCurrentUserId() {
    List<AddressBook> addressBookList = addressBookService.findAllByCurUserId();
    return Result.success(addressBookList);
  }

  @GetMapping("/default")
  @Operation(summary = "查询默认地址")
  public Result findDefaultAddress() {
    AddressBook addressBook = addressBookService.findDefaultAddress();
    return Result.success(addressBook);
  }

  @PutMapping
  @Operation(summary = "根据ID修改地址")
  public Result updateById(@RequestBody AddressBookDto dto) {
    if (addressBookService.updateById(dto)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @DeleteMapping("/")
  @Operation(summary = "根据ID删除地址")
  public Result deleteById(Long id) {
    if (addressBookService.deleteById(id)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @GetMapping("/{id}")
  @Operation(summary = "根据ID查询地址")
  public Result findById(@PathVariable Long id) {
    AddressBook addressBook = addressBookService.findById(id);
    return Result.success(addressBook);
  }

  @PutMapping("/default")
  @Operation(summary = "设置默认地址")
  public Result updateAddrStatus(@RequestBody AddressBookDto dto) {
    if (addressBookService.updateAddrStatus(dto)) {
      return Result.success();
    } else {
      return Result.fail();
    }

  }

}
