package com.herokuapp.obscurespire6277.photor.platform.models;

import com.herokuapp.obscurespire6277.photor.entities.UserEntity;
import jodd.json.meta.JSON;

import java.time.ZonedDateTime;

@JSON(strict = true)
public class User {
    @JSON(name = "id")
    private Long _id;

    @JSON(name = "handle")
    private String _handle;

    @JSON(name = "createdAt")
    private ZonedDateTime _createdAt;

    @JSON(name = "firstName")
    private String _firstName;

    @JSON(name = "lastName")
    private String _lastName;

    private User(Long id, String handle, ZonedDateTime createdAt, String firstName, String lastName) {
        _id = id;
        _handle = handle;
        _createdAt = createdAt;
        _firstName = firstName;
        _lastName = lastName;
    }

    public static User fromHibernateEntity(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getHandle(), userEntity.getCreatedAt(),
            userEntity.getFirstName().get(), userEntity.getLastName().get());
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public String getHandle() {
        return _handle;
    }

    public void setHandle(String handle) {
        _handle = handle;
    }

    public ZonedDateTime getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        _createdAt = createdAt;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }
    //private List<LogIn> logins = new ArrayList<>();
    //private List<Photo> photos = new ArrayList<>();
    //private List<Like> likes = new ArrayList<>();
    //private List<Comment> comments = new ArrayList<>();
}
