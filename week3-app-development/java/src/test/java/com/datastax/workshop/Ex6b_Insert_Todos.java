package com.datastax.workshop;

import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;

@RunWith(JUnitPlatform.class)
@TestMethodOrder(OrderAnnotation.class)
public class Ex6b_Insert_Todos implements DBConnection {
  
  @Test
  @Order(1)
  public void should_insert_todos_1() {
    System.out.println("[should_insert_todos_1] ========================================");
    System.out.println("[should_insert_todos_1] Start with static CQL");
    try (CqlSession cqlSession = TestUtils.createCqlSession()) {
      cqlSession.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) " + 
        "VALUES ( 'john', 11111111-5cff-11ec-be16-1fedb0dfd057, true, 'Walk the dog');"
      );
      
      System.out.println("[should_insert_todos_1] Task '11111111-5cff-11ec-be16-1fedb0dfd057' has been inserted");       
      cqlSession.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) " +
        "VALUES ( 'john', 22222222-5cff-11ec-be16-1fedb0dfd057, false, 'Have lunch tomorrow');"
      );
      System.out.println("[should_insert_todos_1] Task '22222222-5cff-11ec-be16-1fedb0dfd057' has been inserted"); 
      cqlSession.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) " + 
        "VALUES ( 'mary', 33333333-5cff-11ec-be16-1fedb0dfd057, true, 'Attend the workshop');"
      );
      System.out.println("[should_insert_todos_1] Task '33333333-5cff-11ec-be16-1fedb0dfd057' has been inserted"); 
    }
    System.out.println("[should_insert_todos_1] [OK]");
    System.out.println("[should_insert_todos_1] ========================================\n");
  }
  
  @Test
  @Order(2)
  public void should_create_task_cql() {
      System.out.println("[should_create_task_cql] ========================================");
      System.out.println("[should_create_task_cql] Start Exercise");
      try (CqlSession cqlSession = TestUtils.createCqlSession()) {
          cqlSession.execute(""
              + "INSERT INTO todoitems (user_id, item_id, title, completed) "
              + "VALUES('createTask'," +  Uuids.timeBased() + ",'Task CQL',FALSE)");
      }
      System.out.println("[should_create_task_cql] [OK]");
      System.out.println("[should_create_task_cql] ========================================\n");
  }
  
  @Test
  @Order(3)
  public void should_insert_task_simple_position() {
      System.out.println("[should_insert_task_simple_position] ========================================");
      System.out.println("[should_insert_task_simple_position] Start Exercise");
      try (CqlSession cqlSession = TestUtils.createCqlSession()) {
          UUID itemId =  Uuids.timeBased();
          cqlSession.execute(SimpleStatement.builder(""
                  + "INSERT INTO todoitems (user_id, item_id, title, completed) "
                  + "VALUES(?,?,?,?)")
                  .addPositionalValue("createTask")
                  .addPositionalValue(itemId)
                  .addPositionalValue("Task Simple Statement position")
                  .addPositionalValue(Boolean.FALSE)
                  .build());
          System.out.println("[should_insert_task_simple_position] Task " + itemId + " has been inserted");        
      }
      System.out.println("[should_insert_task_simple_position] [OK]");
      System.out.println("[should_insert_task_simple_position] ========================================\n");
  }
  
  @Test
  @Order(4)
  public void should_insert_task_simple_label() {
      System.out.println("[should_insert_task_simple_label] ========================================");
      System.out.println("[should_insert_task_simple_label] Start Exercise");
      try (CqlSession cqlSession = TestUtils.createCqlSession()) {
          UUID itemId =  Uuids.timeBased();
          cqlSession.execute(SimpleStatement.builder(""
                  + "INSERT INTO todoitems (user_id, item_id, title, completed) "
                  + "VALUES(:userid,:itemid,:title,:completed)")
                  .addNamedValue("userid","createTask")
                  .addNamedValue("itemid",itemId)
                  .addNamedValue("title","Task Simple Statement name")
                  .addNamedValue("completed", Boolean.FALSE)
                  .build());
          System.out.println("[should_insert_task_simple_label] Task " + itemId + " has been inserted");     
      }
      System.out.println("[should_insert_task_simple_label] [OK]");
      System.out.println("[should_insert_task_simple_label] ========================================\n");
  }
  
  @Test
  @Order(5)
  public void should_insert_task_prepared() {
      System.out.println("[should_insert_task_prepared] ========================================");
      System.out.println("[should_insert_task_prepared] Start Exercise");
      // When
      try (CqlSession cqlSession = TestUtils.createCqlSession()) {
          PreparedStatement ps = cqlSession.prepare(
                  "INSERT INTO todoitems (user_id, item_id, title, completed) "
                  + "VALUES(?,?,?,?)");
          UUID itemId = Uuids.timeBased();
          cqlSession.execute(ps.bind("createTask", itemId, "Prepared Task", Boolean.FALSE));
          System.out.println("[should_insert_task_prepared] Task " + itemId + " has been inserted");               
      }
      System.out.println("[should_insert_task_prepared] [OK]");
      System.out.println("[should_insert_task_prepared] ========================================\n");
  }
  
}
