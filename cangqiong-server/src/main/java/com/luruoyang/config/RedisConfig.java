package com.luruoyang.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
    log.info("starting to construct redisTemplate");
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();

    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

    redisTemplate.setKeySerializer(stringRedisSerializer);
    redisTemplate.setValueSerializer(fastJsonRedisSerializer);

    redisTemplate.setConnectionFactory(connectionFactory);

    log.info("Has constructed redisTemplate completely");
    return redisTemplate;

  }

}
