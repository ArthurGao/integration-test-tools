package com.arthur.integrationtest.extension;

import static org.assertj.core.api.Assertions.assertThat;

import com.arthur.integrationtest.extension.config.RedisTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({RedisTestConfig.class})
@ExtendWith({SpringExtension.class, RedisExtension.class})
public class RedisExtensionITest {

  @Autowired
  private RedissonClient redissonClient;

  @Test
  void testRedisConnection() {
    RList<String> nameList = redissonClient.getList("nameList");
    nameList.clear();
    nameList.add("test");
    assertThat(nameList).hasSize(1);
    assertThat(nameList).contains("test");
    redissonClient.shutdown();
  }
}
