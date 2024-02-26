package ru.itmo.wp.form;

import ru.itmo.wp.domain.Tag;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

public class PostCredentials {
    @NotNull
//    @NotBlank
    @Size(max=15000)
    private String tags;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65000)
//    @Lob
    private String text;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
