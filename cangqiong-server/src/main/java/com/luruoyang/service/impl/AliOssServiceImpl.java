package com.luruoyang.service.impl;

import com.luruoyang.service.AliOssService;
import com.luruoyang.utils.AliOssUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AliOssServiceImpl implements AliOssService {

  @Autowired
  private AliOssUtils aliOssUtils;

  @Override
  public String upload(MultipartFile multipartFile) {
    return aliOssUtils.upload(multipartFile);
  }

  @Override
  public boolean deleteByFilename(String filename) {
    return aliOssUtils.deleteByFilename(filename);

  }

  @Override
  public void download(String filename, HttpServletResponse response) {
    aliOssUtils.download(filename, response);
  }
}
