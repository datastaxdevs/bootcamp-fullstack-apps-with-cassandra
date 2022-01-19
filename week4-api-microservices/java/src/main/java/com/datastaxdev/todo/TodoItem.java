package com.datastaxdev.todo;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.driver.api.core.uuid.Uuids;

@Table("todoitems")
public class TodoItem {
    
    @PrimaryKey
    private TodoItemKey key = new TodoItemKey("default", Uuids.timeBased());
    
    @Column("title")
    private String title;
    
    @Column("completed")
    private boolean completed = false;
    
    @Column("offset")
    private Integer offset = 0;
    
    /**
     * Default Constructor.
     */
    public TodoItem() {}
    
    /**
     * Simple constructor.
     *
     * @param userid
     *      user id
     * @param title
     *          title
     */
    public TodoItem(String userid, String title) {
        this.title = title;
        this.key.setUserId(userid);
    }
   
    /**
     * Getter accessor for attribute 'key'.
     *
     * @return
     *       current value of 'key'
     */
    public TodoItemKey getKey() {
        return key;
    }

    /**
     * Setter accessor for attribute 'key'.
     * @param key
     * 		new value for 'key '
     */
    public void setKey(TodoItemKey key) {
        this.key = key;
    }

    /**
     * Getter accessor for attribute 'title'.
     *
     * @return
     *       current value of 'title'
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter accessor for attribute 'title'.
     * @param title
     * 		new value for 'title '
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter accessor for attribute 'completed'.
     *
     * @return
     *       current value of 'completed'
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Setter accessor for attribute 'completed'.
     * @param completed
     * 		new value for 'completed '
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Getter accessor for attribute 'offset'.
     *
     * @return
     *       current value of 'offset'
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * Setter accessor for attribute 'offset'.
     * @param offset
     * 		new value for 'offset '
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
}
