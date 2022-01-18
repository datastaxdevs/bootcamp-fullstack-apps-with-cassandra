package com.datastax.workshop;

import java.nio.file.Paths;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

@RunWith(JUnitPlatform.class)
@TestMethodOrder(OrderAnnotation.class)
public class Ex6c_Get_All_Todos implements DBConnection {

  /** Logger for the class. */
  private static Logger LOGGER = LoggerFactory.getLogger("Exercise 6c");

  @Test
  @Order(1)
  public void should_get_todos_1() {
    LOGGER.info("========================================");
    LOGGER.info("should_get_todos (john)");
    try (CqlSession cqlSession = createCqlSession()) {
      ResultSet rs = cqlSession.execute(
        "SELECT * FROM todoitems WHERE user_id = 'john';"
      );
      for (Row row : rs) {
        LOGGER.info(row.getUuid("item_id") + ": " + row.getString("title"));
      }
    }
    LOGGER.info("[OK]");
  }
  
  @Test
  @Order(2)
  public void should_get_todos_2() {
    LOGGER.info("========================================");
    LOGGER.info("should_get_todos_2 (createTask)");
    try (CqlSession cqlSession = createCqlSession()) {
        SimpleStatement query = SimpleStatement.builder(""
                + "SELECT item_id,title,completed "
                + "FROM todoitems WHERE user_id=?")
                .addPositionalValue("createTask")
                .build();
        cqlSession.execute(query).forEach(this::showRow);
    }
    LOGGER.info("[OK]");
    LOGGER.info("========================================");
  }
  
  private CqlSession createCqlSession() {
      return CqlSession
              .builder()
              .withCloudSecureConnectBundle(Paths.get(DBConnection.SECURE_CONNECT_BUNDLE))
              .withAuthCredentials(DBConnection.USERNAME, DBConnection.PASSWORD)
              .withKeyspace(DBConnection.KEYSPACE)
              .build();
  }
  
  private void showRow(Row row) {
      LOGGER.info(row.getUuid("item_id") + ": " + row.getString("title"));
      
  }
}
