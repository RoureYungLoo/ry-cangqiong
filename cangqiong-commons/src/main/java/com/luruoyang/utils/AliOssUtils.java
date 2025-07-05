package com.luruoyang.utils;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.VoidResult;
import com.aliyuncs.exceptions.ClientException;
import com.luruoyang.properties.AliOssProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
@Slf4j
public class AliOssUtils {

  @Autowired
  private AliOssProperties aliOssProperties;

  public String upload(MultipartFile file) {
    String endpoint = aliOssProperties.getEndpoint();
    String bucketName = aliOssProperties.getBucketName();
    OSS ossClient = getOssClient();

    String objectName = file.getOriginalFilename();

    if (Objects.nonNull(objectName)) {
      objectName = getDirsByFileName(objectName) + objectName;
    }

    try {
      ossClient.putObject(bucketName, objectName, file.getInputStream());
    } catch (IOException e) {
      log.error("{}", e.getMessage());
      throw new RuntimeException(e);
    }

    ossClient.shutdown();

    //https://BucketName.Endpoint/ObjectName
    return String.format("https://%s.%s/%s", bucketName, endpoint, objectName);

  }

  private OSS getOssClient() {
    String endpoint = aliOssProperties.getEndpoint();
    String region = aliOssProperties.getRegion();
    EnvironmentVariableCredentialsProvider credentialsProvider = null;
    try {
      credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    } catch (ClientException e) {
      log.error("{}", e.getMessage());
      throw new RuntimeException(e);
    }

    ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
    clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
    OSS ossClient = OSSClientBuilder.create()
        .endpoint(endpoint)
        .credentialsProvider(credentialsProvider)
        .region(region)
        .build();
    return ossClient;
  }

  public boolean deleteByFilename(String filename) {
    OSS ossClient = getOssClient();
    boolean res = false;
    try {
      String objectName = getDirsByFileName(filename) + filename;
      ossClient.deleteObject(aliOssProperties.getBucketName(), objectName);
    } catch (OSSException oe) {
      log.error("{}", oe.getMessage());
      log.error("{}", oe);
      throw oe;
    } finally {
      res = true;
      log.info("删除成功 bucket: {} {}");
      ossClient.shutdown();
    }
    return res;
  }

  public void download(String filename, HttpServletResponse response) {
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));

    try {
      // 获取OSS文件对象
      String objectName = getDirsByFileName(filename) + filename;

      OSS ossClient = getOssClient();
      OSSObject object = ossClient.getObject(aliOssProperties.getBucketName(), objectName);
      try (InputStream inputStream = object.getObjectContent();
           OutputStream outputStream = response.getOutputStream()) {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("文件下载失败");
    } catch (OSSException e) {
      e.printStackTrace();
      throw new RuntimeException("文件不存在或下载失败");
    }
  }

  public static String getDirsByFileName(String filename) {
    /* 不同用户的相同文件名会替换文件, bug */
    int filenameHash = filename.hashCode();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      int subDir = filenameHash & 15;
      sb.append(subDir).append("/");
      filenameHash >>= 4;
    }
    return sb.toString();
  }
}
