package com.arthur.integrationtest.extension.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.arthur.integrationtest.extension.LocalStackExtension;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;

public class AWSDynamoDBV1TestFactory {

  private AWSDynamoDBV1TestFactory() {
  }

  public static AmazonDynamoDB amazonDynamoDB() {
    return AmazonDynamoDBClientBuilder.standard()
        .withCredentials(LocalStackExtension.getLocalstackContainer().getDefaultCredentialsProvider())
        .withEndpointConfiguration(LocalStackExtension.getLocalstackContainer().getEndpointConfiguration(Service.DYNAMODB))
        .build();
  }

}
