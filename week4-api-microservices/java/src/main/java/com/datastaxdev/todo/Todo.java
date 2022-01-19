package com.datastaxdev.todo;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * Class to TODO
 *
 * @author Cedrick LUNVEN (@clunven)
 */
public class Todo {
    
    private String  url;
    private UUID    uuid;
    private String  title;
    private boolean completed = false;
    private Integer order = 0;
    
    public Todo() {}
    
    public Todo(String title, Integer order) {
        this.uuid  = UUID.randomUUID();
        this.title = title;
        this.order = order;
    }
    
    public Todo(String title, int order, boolean completed) {
       this(title, order);
       this.completed = completed;
    }
    
    public Todo setUrl(HttpServletRequest req) {
        if (url == null) {
            String reqUrl = req.getRequestURL().toString();
            url = reqUrl.contains("gitpod") ? reqUrl.replaceAll("http", "https") : reqUrl;
            url += uuid;
        }
        return this;
    }
    
    public Todo setUrl(String myUrl) {
        if (url == null) {
            url = myUrl.contains("gitpod") ? myUrl.replaceAll("http", "https") : myUrl;
        }
        return this;
    }

    /**
     * Getter accessor for attribute 'uuid'.
     *
     * @return
     *       current value of 'uuid'
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Setter accessor for attribute 'uuid'.
     * @param uuid
     * 		new value for 'uuid '
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
     * Getter accessor for attribute 'order'.
     *
     * @return
     *       current value of 'order'
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * Setter accessor for attribute 'order'.
     * @param order
     * 		new value for 'order '
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * Getter accessor for attribute 'url'.
     *
     * @return
     *       current value of 'url'
     */
    public String getUrl() {
        return url;
    }
}
