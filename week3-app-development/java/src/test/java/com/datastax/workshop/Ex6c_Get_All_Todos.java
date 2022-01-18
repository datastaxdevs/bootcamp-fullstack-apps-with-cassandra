package com.datastax.workshop;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

@RunWith(JUnitPlatform.class)
@TestMethodOrder(OrderAnnotation.class)
public class Ex6c_Get_All_Todos implements DBConnection {

  @Test
  @Order(1)
  public void should_get_todos_1() {
    System.out.println("[should_get_todos_1] ========================================");
    System.out.println("[should_get_todos_1] Looking for John Tasks");
    try (CqlSession cqlSession = TestUtils.createCqlSession()) {
      ResultSet rs = cqlSession.execute(
        "SELECT * FROM todoitems WHERE user_id = 'john';"
      );
      for (Row row : rs) {
        System.out.println("[should_get_todos_1] " + 
                row.getUuid("item_id") + ": " + 
                row.getString("title"));
      }
    }
    System.out.println("[should_get_todos_1] [OK]");
    System.out.println("[should_get_todos_1] ========================================\n");
  }
  
  @Test
  @Order(2)
  public void should_get_todos_2() {
    System.out.println("[should_get_todos_2] ========================================");
    System.out.println("[should_get_todos_2] Looking for 'createTask' Tasks");
    try (CqlSession cqlSession = TestUtils.createCqlSession()) {
        SimpleStatement query = SimpleStatement.builder(""
                + "SELECT item_id,title,completed "
                + "FROM todoitems WHERE user_id=?")
                .addPositionalValue("createTask")
                .build();
        cqlSession.execute(query).forEach(row -> 
            System.out.println("[should_get_todos_2] " +
                    row.getUuid("item_id") + ": " + 
                    row.getString("title")));
    }
    System.out.println("[should_get_todos_2] [OK]");
    System.out.println("[should_get_todos_2] ========================================\n");
  }
}
