package com.datastax.workshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;

@RunWith(JUnitPlatform.class)
public class Ex07d_DeleteTask implements DBConnection, DataModel {

    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger("Exercise 10");
    
    @Test
    @DisplayName("Test connectivity to Astra")
    public void should_connect_to_Astra() {
        LOGGER.info("========================================");
        LOGGER.info("Start exercise");
        // When
        try (CqlSession cqlSession = createCqlSession()) {
            LOGGER.info("Connected with Keyspace {}", cqlSession.getKeyspace().get());
        }
        LOGGER.info("SUCCESS");
        LOGGER.info("========================================");
    }
    
}
