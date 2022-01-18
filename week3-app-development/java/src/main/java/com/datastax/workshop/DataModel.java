package com.datastax.workshop;

import static com.datastax.oss.driver.api.core.type.DataTypes.BOOLEAN;
import static com.datastax.oss.driver.api.core.type.DataTypes.TEXT;
import static com.datastax.oss.driver.api.core.type.DataTypes.TIMEUUID;
import static com.datastax.oss.driver.api.core.type.DataTypes.INT;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.dropTable;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;

public interface DataModel {
    
    String TABLE_TODOITEMS   = "todoitems";
    String TODO_USER_ID      = "user_id";
    String TODO_ITEM_ID      = "item_id";
    String TODO_TITLE        = "title";
    String TODO_COMPLETED    = "completed";
    String TODO_OFFSET       = "offset";
    
    String CQL_INSERT = ""
            + "INSERT INTO todoitems (user_id, item_id, title, completed, offset) "
            + "VALUES(?,?,?,?,?)";
    
    String CQL_COMPLETE = ""
            + "UPDATE todoitems SET COMPLETED=TRUE "
            + "WHERE user_id=? and item_id=?";
    
    String CQL_CHANGE_TITLE = ""
            + "UPDATE todoitems "
            + "SET TITLE=:title "
            + "WHERE user_id=:userId AND item_id=:itemId";
    
    /**
     * CREATE TABLE todoitems IF NOT EXISTS (
     *   user_id     text,
     *   item_id timeuuid,
     *   completed boolean,
     *   offset int,
     *   title text,
     *   PRIMARY KEY ((user_id), item_id)
     * ) WITH CLUSTERING ORDER BY (item_id ASC);
     */
    SimpleStatement STMT_CREATE_TABLE_TODOS = 
            createTable(TABLE_TODOITEMS).ifNotExists()
            .withPartitionKey(TODO_USER_ID, TEXT)
            .withClusteringColumn(TODO_ITEM_ID, TIMEUUID)
            .withColumn(TODO_OFFSET, INT)
            .withColumn(TODO_COMPLETED, BOOLEAN)
            .withColumn(TODO_TITLE, TEXT)
            .withClusteringOrder(TODO_ITEM_ID, ClusteringOrder.ASC)
            .build();
    
    SimpleStatement STMT_DROP_TABLE_TODOS = 
            dropTable(TABLE_TODOITEMS).ifExists()
            .build();
    
    default void createSchema(CqlSession cqlSession) {
        cqlSession.execute(STMT_CREATE_TABLE_TODOS);
    }
    
    default void dropSchema(CqlSession cqlSession) {
        cqlSession.execute(STMT_DROP_TABLE_TODOS);
    }
    
    default void truncateTables(CqlSession cqlSession) {
        cqlSession.execute(QueryBuilder.truncate(TABLE_TODOITEMS).build());
    }

}
