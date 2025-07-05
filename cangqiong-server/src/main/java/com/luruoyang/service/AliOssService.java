package com.luruoyang.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;


public interface AliOssService {
  String upload(MultipartFile multipartFile);

  boolean deleteByFilename(String filename);

  void download(String filename, HttpServletResponse response);
}
