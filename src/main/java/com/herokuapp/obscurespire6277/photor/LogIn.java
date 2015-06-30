package com.herokuapp.obscurespire6277.photor;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Optional;

@Entity
@Table(name = "logins")
public class LogIn {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Column(name = "when", nullable = false)
    private ZonedDateTime when;

    @Column(name = "longitude", nullable = true)
    private double longitude;

    @Column(name = "latitude", nullable = true)
    private double latitude;

    @ManyToOne
    private User user;

    // for use by User#addLogIn and tests ONLY
    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public ZonedDateTime getWhen() {
        return when;
    }

    public Optional<Double> getLongitude() {
        return Optional.ofNullable(longitude);
    }

    public Optional<Double> getLatitude() {
        return Optional.ofNullable(latitude);
    }

    public User getUser() {
        return user;
    }

}
