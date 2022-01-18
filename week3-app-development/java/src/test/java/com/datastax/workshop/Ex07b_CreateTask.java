package com.datastax.workshop;

import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
public class Ex07b_CreateTask  implements DBConnection, DataModel {

    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger("Exercise 7b");
    
    @Test
    @Order(1)
    public void should_create_task_cql() {
        LOGGER.info("========================================");
        LOGGER.info("Insert CQL");
        try (CqlSession cqlSession = createCqlSession()) {
            cqlSession.execute(""
                + "INSERT INTO todoitems (user_id, item_id, title, completed, offset) "
                + "VALUES('createTask'," +  Uuids.timeBased() + ",'Task CQL',FALSE,0)");
        }
        LOGGER.info("[OK]");
    }
    
    @Test
    @Order(2)
    public void should_insert_task_simple_position() {
        LOGGER.info("========================================");
        LOGGER.info("Insert Simple Statement (position)");
        try (CqlSession cqlSession = createCqlSession()) {
            UUID itemId =  Uuids.timeBased();
            cqlSession.execute(SimpleStatement.builder(""
                    + "INSERT INTO todoitems (user_id, item_id, title, completed, offset) "
                    + "VALUES(?,?,?,?,?)")
                    .addPositionalValue("createTask")
                    .addPositionalValue(itemId)
                    .addPositionalValue("Task Simple Statement position")
                    .addPositionalValue(Boolean.FALSE)
                    .addPositionalValue(0)
                    .build());
            LOGGER.info("Task '{}' has been inserted", itemId);        
        }
        LOGGER.info("[OK]");
    }
    
    @Test
    @Order(3)
    public void should_insert_task_simple_label() {
        LOGGER.info("========================================");
        LOGGER.info("Insert Simple Statement (named)");
        try (CqlSession cqlSession = createCqlSession()) {
            UUID itemId =  Uuids.timeBased();
            cqlSession.execute(SimpleStatement.builder(""
                    + "INSERT INTO todoitems (user_id, item_id, title, completed, offset) "
                    + "VALUES(:userid,:itemid,:title,:completed,:offset)")
                    .addNamedValue("userid","createTask")
                    .addNamedValue("itemid",itemId)
                    .addNamedValue("title","Task Simple Statement name")
                    .addNamedValue("completed",Boolean.FALSE)
                    .addNamedValue("offset",0)
                    .build());
            LOGGER.info("Task '{}' has been inserted", itemId);        
        }
        LOGGER.info("[OK]");
    }
    
    @Test
    @Order(4)
    public void should_insert_task_prepared() {
        LOGGER.info("========================================");
        LOGGER.info("Insert Prepared Statement");
        // When
        try (CqlSession cqlSession = createCqlSession()) {
            PreparedStatement ps = cqlSession.prepare(CQL_INSERT);
            UUID itemId = Uuids.timeBased();
            cqlSession.execute(ps.bind("createTask", itemId, "Prepared Task", Boolean.FALSE, 0));
            LOGGER.info("Task '{}' has been inserted", itemId);               
        }
        LOGGER.info("[OK]");
    }
    
}
