# Overview

Spring-boot library that will support for TestContainers with Junit 5 extensions model which supply simulator environment for arthur integration test.
See [JUnit 5 Extension Model](https://junit.org/junit5/docs/current/user-guide/#extensions) for more information about Junit 5 extensions.

* LocalStack Extension [`DONE`]
* MockServer Extension [`DONE`]
* MySQL Extension [`DONE`]
* OpenSearch Extension [`DONE`]
* Redis Extension [`DONE`]

# Usage

Any spring-boot application can make use of this library to support simulator environment as below for integration test.

* **LocalStack** - All AWS services e.g., DynamoDN/SQS/SNS/S3/Cloudwatch
* **MockServer** - Http/Https server
* **MySQL** - MySQL server
* **OpenSearch** - OpenSearch server
* **Redis** - Redis server

1. Include the dependency:

```
    <dependency>
      <groupId>com.arthur</groupId>
      <artifactId>arthur-common-integration-test-base</artifactId>
      <version>0.0.1</version>
      <scope>test</scope>
    </dependency>
```

2. LocalStack Extension

Configuration for AWS services in LocalStack. Now LocalStackExtension configuration is supporting DynamoDB and SQS

* Define DynamoDB Configuration to get SpringBoot bean for DynamoDB client with **AWSDynamoDBTestFactory.java** or **AWSDynamoDBV1TestFactory.java**.

**SDK V1**:

```java

@TestConfiguration
public class AWSTestConfig {

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    return AWSDynamoDBV1TestFactory.amazonDynamoDB();
  }
}
``` 

**SDK V2**:

```java

@TestConfiguration
public class AWSTestConfig {

  @Bean
  public DynamoDbClient localstackDynamoDbClient() {
    return AWSDynamoDBTestFactory.localstackDynamoDbClient();
  }
}
```

* Define SQS Configuration to get SpringBoot bean for DynamoDB client with **AWSSQSTestFactory.java** or **AWSSQSV1TestFactory.java**

**SDK V1**:

```java

@TestConfiguration
public class AWSTestConfig {

  public static final String USER_EVENTS_QUEUE_NAME = "user-events-intergation-test";

  @Bean
  @Primary
  public AmazonSQSAsync amazonSQSAsync() {
    return AWSSQSV1TestFactory.amazonSQS(USER_EVENTS_QUEUE_NAME);
  }
}
``` 

**SDK V2**:

```java

@TestConfiguration
public class AWSTestConfig {

  public static final String USER_EVENTS_QUEUE_NAME = "user-events-intergation-test";

  @Bean
  @Primary
  public SqsClient sqsClient() {
    return AWSSQSTestFactory.amazonSQS(USER_EVENTS_QUEUE_NAME);
  }
}
```

**NOTE**: Have to give queue name to be initialized in the configuration when bean created.

ExtendWith LocalStackExtension in your test class. Import corespondent **AWSTestConfig.java** defined above to your test class. This will start LocalStack
container and initialize AWS services.

```java

@ExtendWith(LocalStackExtension.class)
@Import({AWSTestConfig.class})
public class LocalStackExtensionTest {

}
```

3. MockServer Extension

ExtendWith MockServerExtension in your test class. Default port is **1080**.

```java

@ExtendWith({MockServerExtension.class})
class MockServerITest {

}
```

4. MySQL Extension

ExtendWith MockServerExtension in your test class.

```java

@ExtendWith({MySQLExtension.classs})
@MySQLInitScript(dbName = "portal", initScript = "sql/data.sql")
class MySQLITest {

}
```

* **dbName**: Database name
* **initScript**: SQL script to be executed when container is up

5. OpenSearch Extension

ExtendWith OpenSearchExtension in your test class.

```java

import com.arthur.integrationtest.extension.OpenSearchExtension;

@ExtendWith({OpenSearchExtension.classs})
class OpenSearchITest {

}
```

5. Redis Extension Define a bean of type **RedissonClient** in your test class with **RedisTestFactory.java**

```java

@TestConfiguration
public class RedisTestConfig {

  @Bean
  @Primary
  public RedissonClient redissonClient() {
    return RedisTestFactory.redissonClient("localhost", 63790);
  }
}
```

ExtendWith RedisExtension in your test class. Import **RedisTestConfig.java** to your test class.

```java

@Import({RedisTestConfig.class})
@ExtendWith({SpringExtension.class, RedisExtension.class})
public class RedisExtensionITest {

  @Autowired
  private RedissonClient redissonClient;

  @Test
  void testRedisConnection() {
  }
}
``` 

# References

* [JUnit 5](https://junit.org/junit5/docs/current/user-guide/#extensions)
* [TestContainers](https://www.testcontainers.org/)
* [Localstack](https://localstack.cloud/)
* [MockServer](https://www.mock-server.com/)