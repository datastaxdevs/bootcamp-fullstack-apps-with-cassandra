package com.datastax.workshop;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;

@RunWith(JUnitPlatform.class)
public class Ex6a_Create_Table implements DBConnection {

  /** Logger for the class. */
  private static Logger LOGGER = LoggerFactory.getLogger("Exercise 6a");

  @Test
  public void should_create_a_table() {
    LOGGER.info("========================================");
    LOGGER.info("should_create_a_table");
    try (CqlSession cqlSession = CqlSession
        .builder()
        .withCloudSecureConnectBundle(Paths.get(DBConnection.SECURE_CONNECT_BUNDLE))
        .withAuthCredentials(DBConnection.USERNAME, DBConnection.PASSWORD)
        .withKeyspace(DBConnection.KEYSPACE)
        .build()) {
      LOGGER.info("Connected with Keyspace {}", cqlSession.getKeyspace().get());

      String query =
        "CREATE TABLE IF NOT EXISTS todoitems (" +
        "user_id         TEXT," +
        "item_id         TIMEUUID," +
        "title           TEXT," +
        "completed       BOOLEAN," +
        "PRIMARY KEY ((user_id), item_id)" +
        ") WITH CLUSTERING ORDER BY (item_id ASC);";
      cqlSession.execute(query);
      LOGGER.info("Table todoitems created if needed");
    }
    LOGGER.info("[OK]");
  }
}
