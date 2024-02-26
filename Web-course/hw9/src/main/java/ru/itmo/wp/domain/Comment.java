package ru.itmo.wp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.crypto.Data;

import org.hibernate.annotations.CreationTimestamp;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.domain.Post;

import java.util.Date;

@Entity
@Table(indexes = @Index(columnList = "creationTime"))
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Lob
    @Size(min = 1, max = 65000, message = "Should be contains from 1 to 65000 symbols")
    @NotNull
    @NotBlank(message = "Should not contains only whitespaces!")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @CreationTimestamp
    private Date creationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
