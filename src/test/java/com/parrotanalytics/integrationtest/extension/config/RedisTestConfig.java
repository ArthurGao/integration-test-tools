package com.arthur.integrationtest.extension.config;

import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class RedisTestConfig {

  @Bean
  @Primary
  public RedissonClient redissonClient() {
    return RedisTestFactory.redissonClient("localhost", 63790);
  }
}