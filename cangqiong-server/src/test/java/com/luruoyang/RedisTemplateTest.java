package com.luruoyang;

import com.luruoyang.config.RedisConfig;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {CangqiongServerApplication.class})

public class RedisTemplateTest {

  @Autowired
  private RedisTemplate redisTemplate;

  @Test
  public void testRedisTemplate() {

    SetOperations setOperations = redisTemplate.opsForSet();
    System.out.println(setOperations);
  }

  @Test
  public void testRedisString() {
    ValueOperations redis = redisTemplate.opsForValue();
    redis.set("name", "xiaoming");
    System.out.println(redis.get("name"));

    redis.set("code", "1234", 3, TimeUnit.SECONDS);

    redis.setIfAbsent("lock", "1");
    redis.setIfAbsent("lock", "2");

  }

  @Test
  public void testRedisHash() {
    HashOperations redisHash = redisTemplate.opsForHash();
    redisHash.put("person", "name", "李四");
    redisHash.put("person", "age", 28);
    redisHash.put("person", "address", "郑州市");

    System.out.println(redisHash.get("person", "address"));

    System.out.println(redisHash.keys("person"));
    System.out.println(redisHash.values("person"));

  }

  @Test
  public void testRedisList() {
    ListOperations redisList = redisTemplate.opsForList();
    redisList.leftPushAll("alist", "a", "b", "c", "d");
    redisList.leftPush("alist", "e");

    System.out.println(redisList.range("alist", 0, -1));

    System.out.println(redisList.rightPop("alist"));

    System.out.println(redisList.size("alist"));

  }

  @Test
  public void testSet() {
    SetOperations redisSet = redisTemplate.opsForSet();
    String key1 = "hobbies";
    String key2 = "fancies";
    redisSet.add(key1, "swim", "run", "sing", "draw");
    redisSet.add(key2, "sing", "cars", "tree", "draw");

    System.out.println(redisSet.members(key1));
    System.out.println(redisSet.members(key2));

    System.out.println(redisSet.size(key1));
    System.out.println(redisSet.size(key2));

    /*交集*/
    System.out.println(redisSet.intersect(key1, key2));

    /* 并集 */
    System.out.println(redisSet.union(key1, key2));

    redisSet.remove(key1, "run");
    System.out.println(redisSet.members(key1));
  }

  @Test
  public void testZset() {
    ZSetOperations redisZSet = redisTemplate.opsForZSet();
    redisZSet.add("lang", "c", 10.1);
    redisZSet.add("lang", "c++", 10.5);
    redisZSet.add("lang", "java", 10.3);
    System.out.println(redisZSet.remove("lang", 0, -1));
    redisZSet.incrementScore("lang", "c++", 0.2);
  }

  @Test
  public void testCommon() {
    Set keys = redisTemplate.keys("*");
    System.out.println(keys);
    for (Object key : keys) {
      DataType type = redisTemplate.type(key);
      System.out.println(type.name());
    }

    System.out.println(redisTemplate.hasKey("lock"));
    redisTemplate.delete("lock");
    System.out.println(redisTemplate.hasKey("lock"));


  }

}
