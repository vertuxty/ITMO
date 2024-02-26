package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(indexes = @Index(columnList = "name", unique = true))
public class Tag {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
//    @NotBlank(message = "Should not have only whitespaces!")
    @Size(min = 0, max = 15)
    @Pattern(regexp = "\\p{IsLatin}+", message = "Only latin letters allowed in tags!")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
