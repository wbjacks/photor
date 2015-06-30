package com.herokuapp.obscurespire6277.photor.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
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

    @Column(name = "handle", nullable = false)
    private String handle;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = PERSIST, fetch = LAZY)
    private List<LogIn> logins = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST, fetch = LAZY)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST, fetch = LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST, fetch = LAZY)
    private List<Comment> comments = new ArrayList<>();

    public User() { /* hibernate */ }

    public User(String handle, ZonedDateTime createdAt) {
        this.handle = handle;
        this.createdAt = createdAt;
    }

    public void addLogIn(LogIn login) {
        this.logins.add(login);
        login.setUser(this);
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setUser(this);
    }

    public void addLike(Like like) {
        this.likes.add(like);
        like.setUser(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    public Long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Optional<String> getFirstName() {
        return Optional.ofNullable(firstName);
    }

    public Optional<String> getLastName() {
        return Optional.ofNullable(lastName);
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
