package com.datastax.workshop;

import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

@RunWith(JUnitPlatform.class)
@TestMethodOrder(OrderAnnotation.class)
public class Ex6d_Update_A_Todo implements DBConnection {

  @Test
  @Order(1)
  public void should_update_todos() {
    System.out.println("[should_update_todos] ========================================");
    System.out.println("[should_update_todos] Start Exercise");
    // When
    try (CqlSession cqlSession = TestUtils.createCqlSession()) {
      System.out.println("[should_update_todos] Before:");
      TestUtils.showTasks(cqlSession, "john");
      cqlSession.execute(""
        + "UPDATE todoitems "
        + "SET completed = true "
        + "WHERE user_id = 'john' "
        + "AND item_id = 22222222-5cff-11ec-be16-1fedb0dfd057;"
      );
      System.out.println("[should_update_todos] After:");
      TestUtils.showTasks(cqlSession, "john");
    }
    System.out.println("[should_update_todos] [OK]");
    System.out.println("[should_update_todos] ========================================\n");
  }
 
  @Test
  @Order(2)
  public void should_update_todos_2() {
    System.out.println("[should_update_todos_2] ========================================");
    System.out.println("[should_update_todos_2] Start Exercise");
    try (CqlSession cqlSession = TestUtils.createCqlSession()) {
      cqlSession.execute(SimpleStatement.builder(""
              + "UPDATE todoitems "
              + "SET completed=:flag "
              + "WHERE user_id=:userId AND item_id=:itemId")
              .addNamedValue("flag", false)
              .addNamedValue("userId", "john")
              .addNamedValue("itemId", UUID.fromString("11111111-5cff-11ec-be16-1fedb0dfd057"))
              .build());
       TestUtils.showTasks(cqlSession, "john");
    }
    System.out.println("[should_update_todos_2] [OK]");
    System.out.println("[should_update_todos_2] ========================================\n");
  }
   
}
