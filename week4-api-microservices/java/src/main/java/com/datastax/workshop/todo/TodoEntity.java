package com.datastax.workshop.todo;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = TodoEntity.TABLENAME)
public class TodoEntity {
    
    public static final String TABLENAME        = "todos";
    public static final String COLUMN_UID       = "uid";
    public static final String COLUMN_TITLE     = "title";
    public static final String COLUMN_COMPLETED = "completed";
    public static final String COLUMN_ORDER     = "offset";
    
    @PrimaryKey
    @Column(COLUMN_UID)
    @CassandraType(type = Name.UUID)
    private UUID uid;
    
    @Column(COLUMN_TITLE)
    @CassandraType(type = Name.TEXT)
    private String title;
    
    @Column(COLUMN_COMPLETED)
    @CassandraType(type = Name.BOOLEAN)
    private boolean completed = false;
    
    @Column(COLUMN_ORDER)
    @CassandraType(type = Name.INT)
    private int order = 0;
    
    public TodoEntity(String title, int offset) {
        this(UUID.randomUUID(), title, false, offset);
    }

}
