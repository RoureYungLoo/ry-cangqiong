package com.luruoyang.controller.admin;

import com.luruoyang.service.AliOssService;
import com.luruoyang.utils.AliOssUtils;
import com.luruoyang.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/common")
public class CommonController {

  @Autowired
  private AliOssService aliOssService;

  /* 文件上传 */
  @PostMapping("/upload")
  public Result upload(@RequestParam("file") MultipartFile file) {
    String publicPath;
    try {
      publicPath = aliOssService.upload(file);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return publicPath.isEmpty() ? Result.fail() : Result.success(publicPath);
  }
}
