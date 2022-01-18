package com.datastax.workshop;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;

@RunWith(JUnitPlatform.class)
public class Ex07c_UpdateTask  implements DBConnection, DataModel {

    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger("Exercise 7c");
    
    @Test
    public void should_update_tasks() {
        LOGGER.info("========================================");
        LOGGER.info("UpdateTask");
        // When
        try (CqlSession cqlSession = createCqlSession()) {
            UUID itemId =  Uuids.timeBased();
            cqlSession.execute(SimpleStatement.builder(CQL_INSERT)
                    .addPositionalValue("updateTask")
                    .addPositionalValue(itemId)
                    .addPositionalValue("Simple Task Updated")
                    .addPositionalValue(Boolean.FALSE)
                    .addPositionalValue(0)
                    .build());
            LOGGER.info("Task'{}' has been inserted", itemId);   
            
            cqlSession.execute(SimpleStatement.builder(CQL_COMPLETE)
                    .addPositionalValue("updateTask")
                    .addPositionalValue(itemId)
                    .build());
            LOGGER.info("Task'{}' has been completed", itemId);
            
            cqlSession.execute(SimpleStatement.builder(CQL_CHANGE_TITLE)
                    .addNamedValue("title", "New title")
                    .addNamedValue("userId", "updateTask")
                    .addNamedValue("itemId", itemId)
                    .build());
            LOGGER.info("Task'{}' has been updated", itemId);
        }
        LOGGER.info("[OK]");
        LOGGER.info("========================================");
    }
    
}
