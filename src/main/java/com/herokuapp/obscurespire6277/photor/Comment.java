package com.herokuapp.obscurespire6277.photor;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.DateTimeException;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Column(name = "created_at")
    private DateTime createdAt;

    @OneToOne
    private LogIn login;

    @ManyToOne
    private User user;

    @ManyToOne
    private Photo photo;

    public Long getId() {
        return id;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public LogIn getLogin() {
        return login;
    }

    public User getUser() {
        return user;
    }

    public Photo getPhoto() {
        return photo;
    }

}
