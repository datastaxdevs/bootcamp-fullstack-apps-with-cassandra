package com.datastax.workshop;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.datastax.oss.driver.api.core.CqlSession;

@RunWith(JUnitPlatform.class)
public class Ex6a_Create_Table implements DBConnection {
  
  @Test
  public void should_create_a_table_1() {
    System.out.println("[should_create_a_table_1] ========================================");
    System.out.println("[should_create_a_table_1] Start exercise");
    
    try (CqlSession cqlSession = TestUtils.createCqlSession()) {
      String query =
        "CREATE TABLE IF NOT EXISTS todoitems (" +
        "user_id         TEXT," +
        "item_id         TIMEUUID," +
        "title           TEXT," +
        "completed       BOOLEAN," +
        "PRIMARY KEY ((user_id), item_id)" +
        ") WITH CLUSTERING ORDER BY (item_id ASC);";
      cqlSession.execute(query);
    }
    
    System.out.println("[should_create_a_table_1] [OK]");
    System.out.println("[should_create_a_table_1] ========================================");
  }
  
  @Test
  public void should_create_a_table_2() {
    System.out.println("[should_create_a_table_2] ========================================");
    System.out.println("[should_create_a_table_2] Start exercise");
    try (CqlSession cqlSession = TestUtils.createCqlSession()) {
        TestUtils.createTableTodoItem(cqlSession);
    }
    System.out.println("[should_create_a_table_2] [OK]");
    System.out.println("[should_create_a_table_2] ========================================");
  }
  
  
}
