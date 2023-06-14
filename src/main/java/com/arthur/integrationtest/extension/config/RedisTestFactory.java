package com.arthur.integrationtest.extension.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;

public class RedisTestFactory {

  private RedisTestFactory() {
  }

  public static RedissonClient redissonClient(String host, int port) {
    Config config = new Config();
    config.useMasterSlaveServers()
        .setMasterAddress(
            String.format("redis://%s:%s", host, port)
        ).setReadMode(ReadMode.MASTER);
    config.setCodec(new StringCodec());
    return Redisson.create(config);
  }
}
