package com.arthur.integrationtest.extension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.TestcontainersConfiguration;

public class MySQLExtension implements BeforeAllCallback, AfterAllCallback {

  private MySQLContainer<?> mySqlDB;

  @Override
  public void beforeAll(ExtensionContext context) {
    String dbName = Optional.ofNullable(context.getRequiredTestClass().getAnnotation(MySQLInitScript.class))
        .map(MySQLInitScript::dbName)
        .orElseThrow(() -> new RuntimeException("dnName is required"));

    String initScript = Optional.ofNullable(context.getRequiredTestClass().getAnnotation(MySQLInitScript.class))
        .map(MySQLInitScript::initScript)
        .orElseThrow(() -> new RuntimeException("initScript is required"));

    mySqlDB = new MySQLContainer<>("mysql:5.7.37")
        .withDatabaseName(dbName)
        .withUsername("root")
        .withPassword("password");

    if (!StringUtils.isEmpty(initScript)) {
      mySqlDB = mySqlDB.withInitScript(initScript);
    }

    mySqlDB.start();
    mySqlDB.withReuse(true);
    Runtime.getRuntime()
        .addShutdownHook(new Thread(mySqlDB::stop));

    TestcontainersConfiguration.getInstance().updateUserConfig("testcontainers.reuse.enable", "true");
    System.setProperty("spring.datasource.url", mySqlDB.getJdbcUrl());
    System.setProperty("spring.datasource.username", mySqlDB.getUsername());
    System.setProperty("spring.datasource.password", mySqlDB.getPassword());
  }

  @Override
  public void afterAll(ExtensionContext context) {
    // do nothing, Testcontainers handles container shutdown
  }

  @Retention(RetentionPolicy.RUNTIME)
  public @interface MySQLInitScript {

    public String dbName();

    public String initScript();
  }
}


