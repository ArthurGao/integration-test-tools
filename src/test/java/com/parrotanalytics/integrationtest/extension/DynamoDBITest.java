package com.arthur.integrationtest.extension;

import com.arthur.integrationtest.extension.config.AWSDynamoTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

@ExtendWith({SpringExtension.class, LocalStackExtension.class})
@Import(value = {AWSDynamoTestConfig.class})
public class DynamoDBITest {

  @Autowired
  private DynamoDbClient dynamoDbClient;

  @Test
  void testDynamoDBConnection() {
    String tableName = "testTable";
    String key = "testKey";

    DynamoDbWaiter dbWaiter = dynamoDbClient.waiter();
    CreateTableRequest request = CreateTableRequest.builder()
        .attributeDefinitions(AttributeDefinition.builder()
            .attributeName(key)
            .attributeType(ScalarAttributeType.S)
            .build())
        .keySchema(KeySchemaElement.builder()
            .attributeName(key)
            .keyType(KeyType.HASH)
            .build())
        .provisionedThroughput(ProvisionedThroughput.builder()
            .readCapacityUnits(10L)
            .writeCapacityUnits(10L)
            .build())
        .tableName(tableName)
        .build();

    String newTable = "";
    try {
      CreateTableResponse response = dynamoDbClient.createTable(request);
      DescribeTableRequest tableRequest = DescribeTableRequest.builder()
          .tableName(tableName)
          .build();

      // Wait until the Amazon DynamoDB table is created
      WaiterResponse<DescribeTableResponse> waiterResponse = dbWaiter.waitUntilTableExists(tableRequest);
      waiterResponse.matched().response().ifPresent(System.out::println);

      newTable = response.tableDescription().tableName();

    } catch (DynamoDbException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

  }
}
