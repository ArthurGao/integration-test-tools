package com.arthur.integrationtest.extension.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.arthur.integrationtest.extension.LocalStackExtension;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;

public abstract class AWSSQSV1TestFactory {

  private AWSSQSV1TestFactory() {
  }

  public static AmazonSQSAsync amazonSQS(String queueName) {
    final AmazonSQSAsync client = AmazonSQSAsyncClientBuilder.standard()
        .withCredentials(LocalStackExtension.getLocalstackContainer().getDefaultCredentialsProvider())
        .withEndpointConfiguration(LocalStackExtension.getLocalstackContainer().getEndpointConfiguration(Service.SQS))
        .build();
    CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
    createQueueRequest.addAttributesEntry("VisibilityTimeout", "1");
    CreateQueueResult createQueueResult = client.createQueue(createQueueRequest);
    createQueueResult.getQueueUrl();
    return client;
  }
}
