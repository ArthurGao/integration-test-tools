package com.arthur.integrationtest.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.configuration.Configuration;
import org.mockserver.integration.ClientAndServer;

public class MockServerExtension implements BeforeAllCallback, AfterAllCallback {

  private static ClientAndServer mockServer;

  static {
    Configuration config = Configuration.configuration().maxSocketTimeoutInMillis(120000L);
    mockServer = ClientAndServer.startClientAndServer(config, 1080);
  }

  @Override
  public void beforeAll(ExtensionContext context) {
    // do nothing
  }

  @Override
  public void afterAll(ExtensionContext context) {
    mockServer.stop();
  }

  public static ClientAndServer getMockServer() {
    return mockServer;
  }
}
