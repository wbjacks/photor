package com.herokuapp.obscurespire6277.photor.entities;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.HibernateEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "likes")
public class Like implements HibernateEntity {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @OneToOne
    private LogIn login;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private Photo photo;

    public Like() { /* hibernate */ }

    public Like(ZonedDateTime createdAt, LogIn login) {
        this.createdAt = createdAt;
        this.login = login;
    }

    // for use by User#addLike and tests ONLY
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    // for use by UserEntity#addPhoto and tests ONLY
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public Long getId() {
        return id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public LogIn getLogin() {
        return login;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public Photo getPhoto() {
        return photo;
    }

}
