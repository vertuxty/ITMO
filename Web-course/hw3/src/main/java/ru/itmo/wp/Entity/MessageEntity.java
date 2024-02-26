package ru.itmo.wp.Entity;

public class MessageEntity {

    private final String user;
    private final String text;

    public MessageEntity(String user, String text) {
        this.user = user;
        this.text = text;
    }

    @Override
    public String toString() {
        return "{" +
                 user +
                ":" + text +
                "}";
    }
}
