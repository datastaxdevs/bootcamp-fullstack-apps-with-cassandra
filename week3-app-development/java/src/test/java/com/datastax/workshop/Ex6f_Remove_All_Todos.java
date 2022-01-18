package com.datastax.workshop;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.datastax.oss.driver.api.core.CqlSession;

@RunWith(JUnitPlatform.class)
public class Ex6f_Remove_All_Todos implements DBConnection {

  @Test
  public void should_remote_all_todos() {
      System.out.println("[should_remote_all_todos] ========================================");
      System.out.println("[should_remote_all_todos] Start Exercise");
      try (CqlSession cqlSession = TestUtils.createCqlSession()) {
          cqlSession.execute("TRUNCATE TABLE todoitems;");
          TestUtils.showTasks(cqlSession, "john");
      }
      System.out.println("[should_remote_all_todos] [OK]");
      System.out.println("[should_remote_all_todos] ========================================\n");
  }
}
