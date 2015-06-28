package com.herokuapp.obscurespire6277.photor;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "created_at", nullable = false)
    private DateTime createdAt;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "caption", nullable = true)
    private String caption;

    @Column(name = "photo_blob", nullable = false)
    private Blob photoBlob;

    @OneToOne
    private LogIn login;

    @OneToMany(mappedBy = "photo", cascade = PERSIST, fetch = EAGER)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "photo", cascade = PERSIST, fetch = EAGER)
    private List<Comment> comments = new ArrayList<>();


    public Photo() { /* hibernate */ }

    public Photo(User user, DateTime createdAt, String caption) {
        this.user = user;
        this.createdAt = createdAt;
        this.caption = caption;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Optional<String> getCaption() {
        return Optional.ofNullable(caption);
    }

    public Blob getPhotoBlob() {
        return photoBlob;
    }

    public LogIn getLogin() {
        return login;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

}
