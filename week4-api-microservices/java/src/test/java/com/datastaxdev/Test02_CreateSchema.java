package com.datastaxdev;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

//--> Eclipse Support
@SuppressWarnings("deprecation")
@RunWith(JUnitPlatform.class)
//<--

@SpringJUnitConfig
@TestPropertySource(locations="/application.properties")
public class Test02_CreateSchema {

    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger(Test02_CreateSchema.class);

    @Value("${spring.data.cassandra.username}")
    private String username;
    
    @Value("${spring.data.cassandra.password}")
    private String password;
    
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;
    
    @Value("${datastax.astra.secure-connect-bundle}")
    private String cloudSecureBundle;
    
    @Test
    @DisplayName("Test to create a table in ASTRA")
    public void should_create_expected_table() {
        // Given interface is properly populated
        Assertions.assertTrue(new File(cloudSecureBundle).exists(), 
                    "File '" + cloudSecureBundle + "' has not been found\n"
                    + "To run this sample you need to download the secure bundle file from ASTRA WebPage\n"
                    + "More info here:");
        
        // When connecting to ASTRA
        try (CqlSession cqlSession = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(cloudSecureBundle))
                .withAuthCredentials(username, password)
                .withKeyspace(keyspace)
                .build()) {
            LOGGER.info("Connection Established to Astra with Keyspace '{}'", 
                    cqlSession.getKeyspace().get());
            SimpleStatement stmtCreateTable = SchemaBuilder
                    .createTable("todoitems")
                    .ifNotExists()
                    .withPartitionKey("user_id", DataTypes.UUID)
                    .withClusteringColumn("item_id", DataTypes.UUID)
                    .withColumn("title",     DataTypes.TEXT)
                    .withColumn("completed", DataTypes.BOOLEAN)
                    .withColumn("offset",     DataTypes.INT)
                    .build();
            
            // When creating the table
            cqlSession.execute(stmtCreateTable);
            
            // Then table is created
            LOGGER.info("Table '{}' has been created (if needed).", "todoitems");
        }
    }
}
