package com.herokuapp.obscurespire6277.photor;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Column(name = "created_at", nullable = false)
    private DateTime createdAt;

    @OneToOne
    @Column(name = "login_id", nullable = false)
    private LogIn login;

    @ManyToOne
    @Column(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @Column(name = "photo_id", nullable = false)
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
