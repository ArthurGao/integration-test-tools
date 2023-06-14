package com.arthur.integrationtest.extension.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@TestConfiguration
public class AWSDynamoTestConfig {

  @Bean
  public DynamoDbClient localstackDynamoDbClient() {
    return AWSDynamoDBTestFactory.localstackDynamoDbClient();
  }
}
