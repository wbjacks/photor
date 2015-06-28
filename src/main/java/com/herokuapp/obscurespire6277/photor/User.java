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

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    @Column(name = "handle", unique = true, nullable = false)
    private String handle;

    @Column(name = "created_at", nullable = false)
    private DateTime createdAt;

    @Column(name = "last_login", nullable = true)
    private LogIn lastLogin;

    @OneToMany(mappedBy = "users", cascade = PERSIST, fetch = LAZY)
    private List<LogIn> logins = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = PERSIST, fetch = LAZY)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = PERSIST, fetch = LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = PERSIST, fetch = LAZY)
    private List<Comment> comments = new ArrayList<>();

    public User() { /* hibernate */ }

    public User(String firstName, String lastName, String handle, List<Photo> photos) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.photos = photos;
    }

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

    public Optional<LogIn> getLastLogin() {
        return Optional.ofNullable(lastLogin);
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
