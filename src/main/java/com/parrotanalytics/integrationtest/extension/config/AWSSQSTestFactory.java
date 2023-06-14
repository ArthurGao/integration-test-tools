package com.arthur.integrationtest.extension.config;

import com.arthur.integrationtest.extension.LocalStackExtension;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;

public class AWSSQSTestFactory extends AWSTestFactoryBase {

  private AWSSQSTestFactory() {
  }

  public static SqsClient amazonSQS(String queueName) {

    SqsClient sqsClient = SqsClient.builder()
        .endpointOverride(LocalStackExtension.getLocalstackContainer().getEndpointOverride(Service.SQS))
        .region(localstackRegion())
        .credentialsProvider(localstackAwsCredentialsProvider())
        .build();
    CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
        .queueName(queueName)
        .build();
    sqsClient.createQueue(createQueueRequest);
    return sqsClient;
  }

}
