package com.arthur.integrationtest.extension.config;

import com.arthur.integrationtest.extension.LocalStackExtension;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AWSDynamoDBTestFactory extends AWSTestFactoryBase {

  private AWSDynamoDBTestFactory() {
  }

  public static DynamoDbClient localstackDynamoDbClient() {
    return DynamoDbClient.builder()
        .endpointOverride(LocalStackExtension.getLocalstackContainer().getEndpointOverride(Service.DYNAMODB))
        .credentialsProvider(localstackAwsCredentialsProvider())
        .region(localstackRegion())
        .build();
  }
}
