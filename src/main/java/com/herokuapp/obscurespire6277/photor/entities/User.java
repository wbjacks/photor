package com.herokuapp.obscurespire6277.photor.entities;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.HibernateEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.types.FacebookLongTokenType;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "users")
@SuppressWarnings("unused")
public class User implements HibernateEntity<User> {

    @javax.persistence.Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private long id;

    @Version
    private Long version;

    @Column(name = "handle", nullable = false)
    private String handle;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    @Type(type = "FacebookLongTokenType")
    @Column(name = "facebook_long_token", nullable = true, length = 1000)
    private FacebookLongToken facebookLongToken;

    @Type(type = "FacebookUserIdType")
    @Column(name = "facebook_user_id", nullable = false)
    private FacebookUserId facebookUserId;

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

    @Override
    public Id<User> getId() {
        return Id.of(id);
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

    public void setHandle(String newHandle) {
        this.handle = newHandle;
    }

    public Optional<FacebookLongToken> getFacebookLongToken() {
        return Optional.of(facebookLongToken);
    }

    public void setFacebookLongToken(FacebookLongToken facebookLongToken) {
        this.facebookLongToken = facebookLongToken;
    }

    public FacebookUserId getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(FacebookUserId facebookUserId) {
        this.facebookUserId = facebookUserId;
    }
}
