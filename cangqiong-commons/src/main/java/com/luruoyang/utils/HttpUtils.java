package com.luruoyang.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class HttpUtils {
  public static String doGet(String url, Map<String, String> params) {

    CloseableHttpClient httpClient = HttpClients.createDefault();

    URIBuilder builder = null;
    try {
      builder = new URIBuilder(url);
    } catch (URISyntaxException e) {
      log.error("new URIBuilder error: {}", e.getMessage());
      throw new RuntimeException(e);
    }

    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.addParameter(entry.getKey(), entry.getValue());
    }
    URI uri = null;
    try {
      uri = builder.build();
    } catch (URISyntaxException e) {
      log.error("build url error: {}", e.getMessage());
      throw new RuntimeException(e);
    }

    HttpGet httpGet = new HttpGet(uri);

    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(httpGet);
    } catch (IOException e) {
      log.error("doGet error:{}", e.getMessage());
      throw new RuntimeException(e);
    }


    int statusCode = response.getStatusLine().getStatusCode();
    String res = null;
    if (statusCode == HttpStatus.SC_OK) {
      try {
        res = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      } catch (IOException e) {
        log.error("EntityUtils.toString error: {}", e.getMessage());
        throw new RuntimeException(e);
      }
    }

    try {
      response.close();
      httpClient.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return res;
  }
}
