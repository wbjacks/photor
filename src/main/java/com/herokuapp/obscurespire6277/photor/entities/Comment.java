package com.herokuapp.obscurespire6277.photor.entities;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.HibernateEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "comments")
public class Comment implements HibernateEntity<Comment> {

    @javax.persistence.Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private long id;

    @Version
    private Long version;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "content")
    private String content;

    @OneToOne
    private LogIn login;

    @ManyToOne
    private User user;

    @ManyToOne
    private Photo photo;

    public Comment() { /* hibernate */ }

    public Comment(ZonedDateTime createdAt, String content, LogIn login) {
        this.createdAt = createdAt;
        this.content = content;
        this.login = login;
    }

    // for use by User#addComment and tests ONLY
    public void setUser(User user) {
        this.user = user;
    }

    // for use by Photo#addComment and tests ONLY
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public Id<Comment> getId() {
        return Id.of(id);
    }

    public ZonedDateTime getCreatedAt() {
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
