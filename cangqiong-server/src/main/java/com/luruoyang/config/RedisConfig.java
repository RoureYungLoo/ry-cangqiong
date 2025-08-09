package com.luruoyang.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author luruoyang
 */
@Slf4j
@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);

    // key使用String序列化
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());

    // value使用FastJSON序列化
    // 使用支持字符串的FastJSON序列化器
    FastJson2JsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);
    redisTemplate.setValueSerializer(fastJsonRedisSerializer);
    redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

    // value使用JDK序列化（可以正确处理各种对象包括字符串）
//    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//    redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}

/**
 * Redis使用FastJson序列化
 *
 * @author ruoyi
 */
class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
  public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

  static final Filter AUTO_TYPE_FILTER = JSONReader.autoTypeFilter("com.luruoyang");

  private Class<T> clazz;

  public FastJson2JsonRedisSerializer(Class<T> clazz) {
    super();
    this.clazz = clazz;
  }

  @Override
  public byte[] serialize(T t) throws SerializationException {
    if (t == null) {
      return new byte[0];
    }
    return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
  }

  @Override
  public T deserialize(byte[] bytes) throws SerializationException {
    if (bytes == null || bytes.length <= 0) {
      return null;
    }
    String str = new String(bytes, DEFAULT_CHARSET);

    return JSON.parseObject(str, clazz, AUTO_TYPE_FILTER);
  }
}
