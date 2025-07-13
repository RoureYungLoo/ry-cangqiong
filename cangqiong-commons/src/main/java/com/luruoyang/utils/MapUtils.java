package com.luruoyang.utils;

import com.alibaba.fastjson2.JSONArray;
import lombok.Data;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
@Slf4j
public class MapUtils {

  @Value("${gaode.map.key}")
  private String gaodeMapKey;

  public String getAddressLocation(String address) {
    String urlAddrToLng = "https://restapi.amap.com/v3/geocode/geo";
    HashMap<String, String> params = new HashMap<>();
    params.put("key", gaodeMapKey);
    params.put("address", address);

    String res = HttpUtils.doGet(urlAddrToLng, params);
    System.out.println(res);
    String merchantLocation = JSONObject.parseObject(res).getJSONArray("geocodes").getJSONObject(0).getString("location");
    System.out.println(merchantLocation);
    return merchantLocation;
  }

  public BigDecimal computeDeliveryFee(String origin, String destination) {
    String urlDistance = "https://restapi.amap.com/v5/direction/bicycling";
    Map<String, String> params = new HashMap<>();
    params.put("key", gaodeMapKey);
    params.put("origin", origin);
    params.put("destination", destination);
    params.put("show_fields", "cost");

    String res = HttpUtils.doGet(urlDistance, params);

    JSONObject jsonObject = JSONObject.parseObject(res);
    JSONArray jsonArray = jsonObject.getJSONObject("route").getJSONArray("paths");

    Long distance = jsonArray.getJSONObject(0).getLong("distance");
    Long duration = jsonArray.getJSONObject(0).getLong("duration");

    log.info("{}米, {}分钟", distance, duration / 60);

    BigDecimal fee;
    long baseFee = 3;
    long unitKmFee = 1;
    /* 首公里3元 */
    if (distance <= 1000) {
      fee = new BigDecimal(baseFee);
    } else {
      distance = distance - 1000;
      /* 超出的KM数 */
      long extraKM = distance / 1000;
      long extraM = distance % 1000;
      /* 不足1KM算作1KM */
      if (extraM > 0) {
        extraKM++;
      }
      fee = new BigDecimal(baseFee + extraKM * unitKmFee);
    }


    log.info("配送费用: {}", fee);

    return fee;

  }
}
