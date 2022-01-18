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
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;

@RunWith(JUnitPlatform.class)
@TestMethodOrder(OrderAnnotation.class)
public class Ex6b_Insert_Todos implements DBConnection {

  /** Logger for the class. */
  private static Logger LOGGER = LoggerFactory.getLogger("Exercise 6b");
  
  @Test
  @Order(1)
  public void should_create_todos_1() {
    LOGGER.info("========================================");
    LOGGER.info("Insert CQL 1");
    try (CqlSession cqlSession = createCqlSession()) {
      LOGGER.info("Connected with Keyspace {}", cqlSession.getKeyspace().get());
      cqlSession.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) " + 
        "VALUES ( 'john', 11111111-5cff-11ec-be16-1fedb0dfd057, true, 'Walk the dog');"
      );
      LOGGER.info("Task '11111111-5cff-11ec-be16-1fedb0dfd057' has been inserted");       
      cqlSession.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) " +
        "VALUES ( 'john', 22222222-5cff-11ec-be16-1fedb0dfd057, false, 'Have lunch tomorrow');"
      );
      LOGGER.info("Task '22222222-5cff-11ec-be16-1fedb0dfd057' has been inserted"); 
      cqlSession.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) " + 
        "VALUES ( 'mary', 33333333-5cff-11ec-be16-1fedb0dfd057, true, 'Attend the workshop');"
      );
      LOGGER.info("Task '33333333-5cff-11ec-be16-1fedb0dfd057' has been inserted"); 
    }
    LOGGER.info("[OK]");
  }
  
  @Test
  @Order(2)
  public void should_create_task_cql() {
      LOGGER.info("========================================");
      LOGGER.info("Insert CQL 2");
      try (CqlSession cqlSession = createCqlSession()) {
          cqlSession.execute(""
              + "INSERT INTO todoitems (user_id, item_id, title, completed) "
              + "VALUES('createTask'," +  Uuids.timeBased() + ",'Task CQL',FALSE)");
      }
      LOGGER.info("[OK]");
  }
  
  @Test
  @Order(3)
  public void should_insert_task_simple_position() {
      LOGGER.info("========================================");
      LOGGER.info("Insert Simple Statement (position)");
      try (CqlSession cqlSession = createCqlSession()) {
          UUID itemId =  Uuids.timeBased();
          cqlSession.execute(SimpleStatement.builder(""
                  + "INSERT INTO todoitems (user_id, item_id, title, completed) "
                  + "VALUES(?,?,?,?)")
                  .addPositionalValue("createTask")
                  .addPositionalValue(itemId)
                  .addPositionalValue("Task Simple Statement position")
                  .addPositionalValue(Boolean.FALSE)
                  .build());
          LOGGER.info("Task '{}' has been inserted", itemId);        
      }
      LOGGER.info("[OK]");
  }
  
  @Test
  @Order(4)
  public void should_insert_task_simple_label() {
      LOGGER.info("========================================");
      LOGGER.info("Insert Simple Statement (named)");
      try (CqlSession cqlSession = createCqlSession()) {
          UUID itemId =  Uuids.timeBased();
          cqlSession.execute(SimpleStatement.builder(""
                  + "INSERT INTO todoitems (user_id, item_id, title, completed) "
                  + "VALUES(:userid,:itemid,:title,:completed)")
                  .addNamedValue("userid","createTask")
                  .addNamedValue("itemid",itemId)
                  .addNamedValue("title","Task Simple Statement name")
                  .addNamedValue("completed", Boolean.FALSE)
                  .build());
          LOGGER.info("Task '{}' has been inserted", itemId);        
      }
      LOGGER.info("[OK]");
  }
  
  @Test
  @Order(5)
  public void should_insert_task_prepared() {
      LOGGER.info("========================================");
      LOGGER.info("Insert Prepared Statement");
      // When
      try (CqlSession cqlSession = createCqlSession()) {
          PreparedStatement ps = cqlSession.prepare(
                  "INSERT INTO todoitems (user_id, item_id, title, completed) "
                  + "VALUES(?,?,?,?)");
          UUID itemId = Uuids.timeBased();
          cqlSession.execute(ps.bind("createTask", itemId, "Prepared Task", Boolean.FALSE));
          LOGGER.info("Task '{}' has been inserted", itemId);               
      }
      LOGGER.info("[OK]");
  }
  
  private CqlSession createCqlSession() {
      return CqlSession
              .builder()
              .withCloudSecureConnectBundle(Paths.get(DBConnection.SECURE_CONNECT_BUNDLE))
              .withAuthCredentials(DBConnection.USERNAME, DBConnection.PASSWORD)
              .withKeyspace(DBConnection.KEYSPACE)
              .build();
  }
  
  
}
