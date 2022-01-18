package com.datastax.workshop;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;

@RunWith(JUnitPlatform.class)
public class Ex07a_Create_Schema implements DBConnection, DataModel {

    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger("Exercise 7a");
    
    @Test
    public void should_create_table() {
        LOGGER.info("========================================");
        LOGGER.info("Creating table");
        // When
        try (CqlSession cqlSession = createCqlSession()) {
            LOGGER.info("Connected with Keyspace {}", cqlSession.getKeyspace().get());
            //dropSchema(cqlSession);
            createSchema(cqlSession);
            LOGGER.info("Table {} created if needed", TABLE_TODOITEMS);
        }
        LOGGER.info("[OK]");
        LOGGER.info("========================================");
    }
        
}
