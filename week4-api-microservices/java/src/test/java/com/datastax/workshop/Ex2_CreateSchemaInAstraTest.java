package com.datastax.workshop;

import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.workshop.todo.TodoEntity;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="/application.properties")
public class Ex2_CreateSchemaInAstraTest {

    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger(Ex2_CreateSchemaInAstraTest.class);

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
        try (CqlSession cqlSession = CqlSession.builder()
                //.addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withCloudSecureConnectBundle(Paths.get(cloudSecureBundle))
                .withAuthCredentials(username, password)
                .withKeyspace(keyspace)
                .build()) {
            LOGGER.info("Connection Established to Astra with Keyspace '{}'", 
                    cqlSession.getKeyspace().get());
            SimpleStatement stmtCreateTable = SchemaBuilder
                    .createTable(TodoEntity.TABLENAME)
                    .ifNotExists()
                    .withPartitionKey(TodoEntity.COLUMN_UID, DataTypes.UUID)
                    .withColumn(TodoEntity.COLUMN_TITLE,     DataTypes.TEXT)
                    .withColumn(TodoEntity.COLUMN_COMPLETED, DataTypes.BOOLEAN)
                    .withColumn(TodoEntity.COLUMN_ORDER,     DataTypes.INT)
                    .build();
            
            // When creating the table
            cqlSession.execute(stmtCreateTable);
            
            // Then table is created
            LOGGER.info("Table '{}' has been created (if needed).", TodoEntity.TABLENAME);
        }
    }
}
