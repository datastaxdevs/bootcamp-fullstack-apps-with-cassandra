package com.datastaxdev.todo;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class TodoItemKey {
    
    @PrimaryKeyColumn(name = "user_id",type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String userId;

    @PrimaryKeyColumn(name = "item_id", type = PrimaryKeyType.CLUSTERED, ordinal = 1, ordering = Ordering.ASCENDING)
    @CassandraType(type = Name.TIMEUUID)
    private UUID itemId;

    public TodoItemKey() {}
    
    public TodoItemKey(String userId, UUID itemId) {
        super();
        this.userId = userId;
        this.itemId = itemId;
    }

    /**
     * Getter accessor for attribute 'userId'.
     *
     * @return
     *       current value of 'userId'
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter accessor for attribute 'userId'.
     * @param userId
     * 		new value for 'userId '
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter accessor for attribute 'itemId'.
     *
     * @return
     *       current value of 'itemId'
     */
    public UUID getItemId() {
        return itemId;
    }

    /**
     * Setter accessor for attribute 'itemId'.
     * @param itemId
     * 		new value for 'itemId '
     */
    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }
    
    

}
