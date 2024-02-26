package ru.itmo.wp.model.domain;
import java.util.Date;
public class Event extends Entity {
    private long id;
    private Date creationTime;
    private long userId;
    private types type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public types getType() {
        return type;
    }

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setType(types type) {
        this.type = type;
    }

    public enum types {
        ENTER, LOGOUT
    }
}
