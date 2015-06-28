package com.herokuapp.obscurespire6277.photor;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.EMPTY_LIST;
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
    @Column(name = "user_id", nullable = false)
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

    @Column(name = "hash_tags", nullable = true)
    private List<String> hashTags;

    @OneToOne
    @Column(name = "login_id")
    private LogIn login;

    @OneToMany(mappedBy = "photos", cascade = PERSIST, fetch = EAGER)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "photos", cascade = PERSIST, fetch = EAGER)
    private List<Comment> comments = new ArrayList<>();


    public Photo() { /* hibernate */ }

    public Photo(User user, DateTime createdAt, String caption, List<String> hashTags) {
        this.user = user;
        this.createdAt = createdAt;
        this.caption = caption;
        this.hashTags = hashTags;
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

    public List<String> getHashTags() {
        return hashTags != null ? hashTags : EMPTY_LIST;
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
