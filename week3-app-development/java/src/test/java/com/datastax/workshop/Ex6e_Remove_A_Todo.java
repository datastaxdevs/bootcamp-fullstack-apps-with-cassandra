package com.datastax.workshop;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

@RunWith(JUnitPlatform.class)
public class Ex6e_Remove_A_Todo implements DBConnection {

  @Test
  public void should_remove_todos() {
    System.out.println("[should_remove_todos] ========================================");
    System.out.println("[should_remove_todos] Start Exercise");
    try (CqlSession cqlSession = TestUtils.createCqlSession()) {
        deleteTask(cqlSession, "john", UUID.fromString("11111111-5cff-11ec-be16-1fedb0dfd057"));
        TestUtils.showTasks(cqlSession, "john");
    }
    System.out.println("[should_remove_todos] [OK]");
    System.out.println("[should_remove_todos] ========================================\n");
  }
  
  private void deleteTask(CqlSession cqlSession, String user, UUID taskId) {
      cqlSession.execute(SimpleStatement.builder(""
              + "DELETE FROM todoitems "
              + "WHERE user_id=? "
              + "AND item_id=?")
              .addPositionalValue(user)
              .addPositionalValue(taskId)
              .build());
  }
  
 
}
