package com.datastax.workshop;

import static com.datastax.oss.driver.api.core.type.DataTypes.BOOLEAN;
import static com.datastax.oss.driver.api.core.type.DataTypes.TEXT;
import static com.datastax.oss.driver.api.core.type.DataTypes.TIMEUUID;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

import java.nio.file.Paths;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;

public class TestUtils {
    
    public static final String TABLE_TODOITEMS   = "todoitems";
    public static final String TODO_USER_ID      = "user_id";
    public static final String TODO_ITEM_ID      = "item_id";
    public static final String TODO_TITLE        = "title";
    public static final String TODO_COMPLETED    = "completed";
    public static final String TODO_OFFSET       = "offset";
    
    public static CqlSession createCqlSession() {
        return CqlSession
                .builder()
                .withCloudSecureConnectBundle(Paths.get(DBConnection.SECURE_CONNECT_BUNDLE))
                .withAuthCredentials(DBConnection.USERNAME, DBConnection.PASSWORD)
                .withKeyspace(DBConnection.KEYSPACE)
                .build();
    }
    
    /**
     * CREATE TABLE todoitems IF NOT EXISTS (
     *   user_id     text,
     *   item_id timeuuid,
     *   completed boolean,
     *   title text,
     *   PRIMARY KEY ((user_id), item_id)
     * ) WITH CLUSTERING ORDER BY (item_id ASC);
     */
    public static void createTableTodoItem(CqlSession cqlSession) {
        cqlSession.execute(
            createTable(TABLE_TODOITEMS).ifNotExists()
            .withPartitionKey(TODO_USER_ID, TEXT)
            .withClusteringColumn(TODO_ITEM_ID, TIMEUUID)
            .withColumn(TODO_COMPLETED, BOOLEAN)
            .withColumn(TODO_TITLE, TEXT)
            .withClusteringOrder(TODO_ITEM_ID, ClusteringOrder.ASC)
            .build());
    }
    
    
    public static void showTasks(CqlSession cqlSession, String user) {
        cqlSession.execute(SimpleStatement
                    .builder("SELECT item_id, completed, title  FROM todoitems WHERE user_id=?")
                    .addPositionalValue(user).build())
                  .forEach(row -> {
                    System.out.println("+" + row.getUuid("item_id") + ": " + 
                            row.getBoolean("completed") + ":" + 
                            row.getString("title"));      
                  });
    }

}
