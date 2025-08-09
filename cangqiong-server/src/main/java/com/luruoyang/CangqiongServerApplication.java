package com.luruoyang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class CangqiongServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(CangqiongServerApplication.class, args);
  }

}
