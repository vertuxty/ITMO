package ru.itmo.web.hw4.model;

public class Post {
    private final long id;
    private final String title;
    private final String text;
    private final long user_id;
    private final String creationTime; //что бы красиво было в /users

    public Post(long id, String title, String text, long user_id, String creationTime) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.user_id = user_id;
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getUser_id() {
        return user_id;
    }
    public String getCreationTime() {
        return creationTime;
    }

}
