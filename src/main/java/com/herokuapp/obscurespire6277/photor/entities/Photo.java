package com.herokuapp.obscurespire6277.photor.entities;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.HibernateEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "photos")
public class Photo implements HibernateEntity<Photo> {

    @javax.persistence.Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private long id;

    @Version
    private Long version;

    @ManyToOne
    private User user;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

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

    public Photo(ZonedDateTime createdAt, String caption, Blob photoBlob, LogIn login) {
        this.createdAt = createdAt;
        this.caption = caption;
        this.photoBlob = photoBlob;
        this.login = login;
    }

    /* for use by User#addPhoto and tests ONLY */
    public void setUser(User user) {
        this.user = user;
    }

    public void addLike(Like like) {
        this.likes.add(like);
        like.setPhoto(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPhoto(this);
    }

    @Override
    public Id<Photo> getId() {
        return Id.of(id);
    }

    public User getUser() {
        return user;
    }

    public ZonedDateTime getCreatedAt() {
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
