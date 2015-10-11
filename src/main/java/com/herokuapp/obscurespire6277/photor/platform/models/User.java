package com.herokuapp.obscurespire6277.photor.platform.models;

import jodd.json.meta.JSON;

import java.util.Date;

@JSON(strict = true)
public class User {
    @JSON
    private Long id;
    @JSON
    private String handle;
    @JSON
    private Date createdAt;
    @JSON
    private String firstName;
    @JSON
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    //private List<LogIn> logins = new ArrayList<>();
    //private List<Photo> photos = new ArrayList<>();
    //private List<Like> likes = new ArrayList<>();
    //private List<Comment> comments = new ArrayList<>();
}
