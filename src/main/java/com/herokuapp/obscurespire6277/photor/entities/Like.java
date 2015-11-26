package com.herokuapp.obscurespire6277.photor.entities;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.HibernateEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "likes")
public class Like implements HibernateEntity<Like> {

    @javax.persistence.Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private long id;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

/* TODO: Amith
    @OneToOne
    private LogIn login;
*/

    @ManyToOne
    private User user;

    @ManyToOne
    private Photo photo;

    public Like() { /* hibernate */ }

    public Like(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // for use by User#addLike and tests ONLY
    public void setUser(User user) {
        this.user = user;
    }

    // for use by User#addPhoto and tests ONLY
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public Id<Like> getId() {
        return Id.of(id);
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public Photo getPhoto() {
        return photo;
    }

}
