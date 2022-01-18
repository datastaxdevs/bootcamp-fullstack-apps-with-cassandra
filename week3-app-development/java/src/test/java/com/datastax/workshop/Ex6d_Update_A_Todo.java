package com.datastax.workshop;

import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

@RunWith(JUnitPlatform.class)
@TestMethodOrder(OrderAnnotation.class)
public class Ex6d_Update_A_Todo implements DBConnection {

  /** Logger for the class. */
  private static Logger LOGGER = LoggerFactory.getLogger("Exercise 6d");

  @Test
  @Order(1)
  public void should_update_todos() {
    LOGGER.info("========================================");
    LOGGER.info("should_update_todos 1");

    // When
    try (CqlSession cqlSession = createCqlSession()) {
      LOGGER.info("Before");
      showTasks(cqlSession, "john");
      cqlSession.execute(""
        + "UPDATE todoitems "
        + "SET completed = true "
        + "WHERE user_id = 'john' "
        + "AND item_id = 22222222-5cff-11ec-be16-1fedb0dfd057;"
      );
      LOGGER.info("After:");
      showTasks(cqlSession, "john");
    }
    LOGGER.info("[OK]");
  }
 
  @Test
  @Order(2)
  public void should_update_todos_2() {
    LOGGER.info("========================================");
    LOGGER.info("should_update_todos 2");
    try (CqlSession cqlSession = createCqlSession()) {
      cqlSession.execute(SimpleStatement.builder(""
              + "UPDATE todoitems "
              + "SET completed=:flag "
              + "WHERE user_id=:userId AND item_id=:itemId")
              .addNamedValue("flag", false)
              .addNamedValue("userId", "john")
              .addNamedValue("itemId", UUID.fromString("11111111-5cff-11ec-be16-1fedb0dfd057"))
              .build());
       showTasks(cqlSession, "john");
    }
    LOGGER.info("[OK]");
    LOGGER.info("========================================");
  }
  
  // ======== Utilities ==============
  
  private CqlSession createCqlSession() {
      return CqlSession
              .builder()
              .withCloudSecureConnectBundle(Paths.get(DBConnection.SECURE_CONNECT_BUNDLE))
              .withAuthCredentials(DBConnection.USERNAME, DBConnection.PASSWORD)
              .withKeyspace(DBConnection.KEYSPACE)
              .build();
  }
  
  private void showTasks(CqlSession cqlSession, String user) {
      cqlSession.execute(SimpleStatement
                  .builder("SELECT item_id, completed, title  FROM todoitems WHERE user_id=?")
                  .addPositionalValue(user).build())
                .forEach(row -> {
                  LOGGER.info("+" + row.getUuid("item_id") + ": " + 
                          row.getBoolean("completed") + ":" + 
                          row.getString("title"));      
                });
  }
  
}
