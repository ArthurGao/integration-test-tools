package com.arthur.integrationtest.extension;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

@Log4j2
public class LocalStackExtension implements BeforeAllCallback, AfterAllCallback { //implements ParameterResolver {

  private static final String LOCALSTACK_DOCKER_IMAGE_NAME = "localstack/localstack";
  private static final String LOCALSTACK_DOCKER_VERSION = "1.0.3";
  private static final LocalStackContainer localstackContainer;

  static {
    localstackContainer = new LocalStackContainer(DockerImageName.parse(LOCALSTACK_DOCKER_IMAGE_NAME)
        .withTag(LOCALSTACK_DOCKER_VERSION))
        .withServices(Service.CLOUDWATCH);

    localstackContainer.start();
    localstackContainer.withReuse(true);

    log.info("LocalStackContainer started.");
    Runtime.getRuntime()
        .addShutdownHook(new Thread(localstackContainer::stop));
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    TestcontainersConfiguration.getInstance().updateUserConfig("testcontainers.reuse.enable", "true");
  }

  @Override
  public void afterAll(ExtensionContext context) {
    // do nothing, Testcontainers handles container shutdown
  }

  public static LocalStackContainer getLocalstackContainer() {
    return localstackContainer;
  }
}
