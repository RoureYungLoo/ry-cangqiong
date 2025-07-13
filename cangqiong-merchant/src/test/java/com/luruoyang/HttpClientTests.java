package com.luruoyang;

import com.alibaba.fastjson2.JSONArray;
import com.luruoyang.utils.HttpUtils;
import netscape.javascript.JSObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;

@SpringBootTest
class HttpClientTests {

  private static final Logger log = LoggerFactory.getLogger(HttpClientTests.class);

  @Test
  void contextLoads() {
  }

  @Test
  public void testHttpGet() throws Exception {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");
    httpGet.addHeader("Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi566h55CG5ZGYIiwiaWQiOjEsInVzZXJuYW1lIjoiYWRtaW4iLCJleHAiOjE3NTMwMDE2NzR9.VD-uT-fk5wYoOv8TBtjonas0bO8neEYeBOERpQX3hoo");

    CloseableHttpResponse response = httpClient.execute(httpGet);

    log.info("response: {}", response);

    log.info("response.getStatusLine(): {}", response.getStatusLine());

    int statusCode = response.getStatusLine().getStatusCode();
    System.out.println(statusCode);

    HttpEntity entity = response.getEntity();
    System.out.println(entity);

    String string = EntityUtils.toString(entity);
    System.out.println(string);

    response.close();
    httpClient.close();
  }

  @Test
  public void testHttpPost() throws Exception {

    CloseableHttpClient httpClient = HttpClients.createDefault();

    String url = "http://localhost:8080/admin/employee/login";

    HttpPost httpPost = new HttpPost(url);

    Header header = new BasicHeader("Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi566h55CG5ZGYIiwiaWQiOjEsInVzZXJuYW1lIjoiYWRtaW4iLCJleHAiOjE3NTMwMDE2NzR9.VD-uT-fk5wYoOv8TBtjonas0bO8neEYeBOERpQX3hoo");

    httpPost.addHeader(header);


    JSONObject jsonObject = new JSONObject();
    jsonObject.put("username", "songjiang");
    jsonObject.put("password", "123456");

    StringEntity entity = new StringEntity(jsonObject.toString());

    entity.setContentEncoding("utf-8");
    entity.setContentType("application/json");

    httpPost.setEntity(entity);

    CloseableHttpResponse response = httpClient.execute(httpPost);

    int statusCode = response.getStatusLine().getStatusCode();
    System.out.println(statusCode);

    HttpEntity resEntity = response.getEntity();

    String res = EntityUtils.toString(resEntity);
    System.out.println(res);

    response.close();
    httpClient.close();

  }

  @Value("${baidu.weather.key}")
  private String baiduWeatherKey;

  @Test
  public void testBaiduWeather() throws Exception {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    String weatherApi = "https://api.map.baidu.com/weather/v1/";

    System.out.println("baiduWeatherKey: " + baiduWeatherKey);

    URIBuilder uriBuilder = new URIBuilder(weatherApi);
    uriBuilder.addParameter("data_type", "all");
    uriBuilder.addParameter("ak", baiduWeatherKey);
    uriBuilder.addParameter("location", "116.481488,39.990464");

    URI uri = uriBuilder.build();

    HttpGet httpGet = new HttpGet(uri);

    CloseableHttpResponse response = httpClient.execute(httpGet);

    HttpEntity entity = response.getEntity();

    String res = EntityUtils.toString(entity);

    System.out.println(res);

  }

  @Value("${gaode.map.key}")
  private String gaodeKey;

  @Test
  public void testAddressToLng() throws Exception {
//    String urlAddrToLng = "https://restapi.amap.com/v3/geocode/geo";
//    URIBuilder uriBuilder = new URIBuilder(urlAddrToLng);
//    uriBuilder.addParameter("key", gaodeKey);
//    uriBuilder.addParameter("address", "郑州市中牟县航空港区锦荣悦汇城东门东北70米");
//    URI uri = uriBuilder.build();
//
//    CloseableHttpClient httpClient = HttpClients.createDefault();
//    HttpGet httpGet = new HttpGet(uri);
//
//    CloseableHttpResponse response = httpClient.execute(httpGet);
//
//    System.out.println(response.getStatusLine());
//
//    HttpEntity entity = response.getEntity();
//    String res = EntityUtils.toString(entity);

    String urlAddrToLng = "https://restapi.amap.com/v3/geocode/geo";
    HashMap<String, String> params = new HashMap<>();
    params.put("key", gaodeKey);
    params.put("address", "郑州市中牟县航空港区锦荣悦汇城东门东北70米");

    String res = HttpUtils.doGet(urlAddrToLng, params);
    System.out.println(res);
    String merchantLocation = com.alibaba.fastjson2.JSONObject.parseObject(res).getJSONArray("geocodes").getJSONObject(0).getString("location");
    System.out.println(merchantLocation);


  }

  @Test
  public void testDistance() throws Exception {
    String urlDistance = "https://restapi.amap.com/v5/direction/bicycling";


//    URIBuilder uriBuilder = new URIBuilder(urlDistance);
//    uriBuilder.addParameter("key", gaodeKey);
//    uriBuilder.addParameter("origin", "113.840576,34.571866");
//    uriBuilder.addParameter("destination", "113.861685,34.565051");
//    uriBuilder.addParameter("show_fields", "cost");
//
//    URI uri = uriBuilder.build();
//
//    CloseableHttpClient httpClient = HttpClients.createDefault();
//    HttpGet httpGet = new HttpGet(uri);
//
//    CloseableHttpResponse response = httpClient.execute(httpGet);
//
//    HttpEntity entity = response.getEntity();
//    String res = EntityUtils.toString(entity);
//    System.out.println(res);

    HashMap<String, String> map = new HashMap<>();
    map.put("key", "");
    map.put("origin", "");
    map.put("destination", "");
    map.put("show_fields", "cost");
    String res = HttpUtils.doGet(urlDistance, map);

    com.alibaba.fastjson2.JSONObject jsonObject = com.alibaba.fastjson2.JSONObject.parseObject(res);
    JSONArray jsonArray = jsonObject.getJSONObject("route").getJSONArray("paths");

    Long distance = jsonArray.getJSONObject(0).getLong("distance");
    Long duration = jsonArray.getJSONObject(0).getLong("duration");

    log.info("{}米, {}分钟", distance, duration / 60);

    Double fee = 0d;
    /* 多少KM */
    long km = distance / 1000;
    if (distance % 1000 > 0) {
      km++;
    }

    fee = km + 2d;
    log.info("配送费用: {}", fee);
  }

}
