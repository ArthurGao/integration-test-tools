package com.arthur.integrationtest.extension.config;

import com.arthur.integrationtest.extension.LocalStackExtension;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

public class AWSTestFactoryBase {
  
  protected static AwsCredentialsProvider localstackAwsCredentialsProvider() {
    return StaticCredentialsProvider.create(AwsBasicCredentials.create(
        LocalStackExtension.getLocalstackContainer().getAccessKey(),
        LocalStackExtension.getLocalstackContainer().getSecretKey()
        )
    );
  }

  protected static Region localstackRegion() {
    return Region.of(LocalStackExtension.getLocalstackContainer().getRegion());
  }

}
