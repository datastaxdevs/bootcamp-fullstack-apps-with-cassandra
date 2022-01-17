package com.datastax.workshop;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import java.io.File;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnitPlatform.class)
public class Ex6c_Get_All_Todos implements DBConnection {

  /** Logger for the class. */
  private static Logger LOGGER = LoggerFactory.getLogger("Exercise 6");

  @Test
  public void should_get_todos() {
    LOGGER.info("========================================");
    LOGGER.info("Start exercise");

    // When
    try (
      CqlSession cqlSession = CqlSession
        .builder()
        .withCloudSecureConnectBundle(
          Paths.get(DBConnection.SECURE_CONNECT_BUNDLE)
        )
        .withAuthCredentials(DBConnection.USERNAME, DBConnection.PASSWORD)
        .withKeyspace(DBConnection.KEYSPACE)
        .build()
    ) {
      // Then
      LOGGER.info("Connected with Keyspace {}", cqlSession.getKeyspace().get());

      ResultSet rs = cqlSession.execute(
        "SELECT * FROM todoitems WHERE user_id = 'john';"
      );
      for (Row row : rs) {
        LOGGER.info(row.toString());
      }
    }
    LOGGER.info("SUCCESS");
    LOGGER.info("========================================");
  }
}
