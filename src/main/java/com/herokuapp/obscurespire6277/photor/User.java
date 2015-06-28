package com.herokuapp.obscurespire6277.photor;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "handle")
    private String handle;

    @Column(name = "created_at")
    private DateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<LogIn> logins = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST, fetch = LAZY)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST, fetch = LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST, fetch = LAZY)
    private List<Comment> comments = new ArrayList<>();

    public User() { /* hibernate */ }

    public Long getId() {
        return id;
    }

    public Optional<String> getFirstName() {
        return Optional.ofNullable(firstName);
    }

    public Optional<String> getLastName() {
        return Optional.ofNullable(lastName);
    }

    public String getHandle() {
        return handle;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public List<LogIn> getLogins() {
        return logins;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

}
