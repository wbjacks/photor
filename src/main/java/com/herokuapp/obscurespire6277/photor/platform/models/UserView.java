package com.herokuapp.obscurespire6277.photor.platform.models;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import jodd.json.meta.JSON;

import java.time.ZonedDateTime;

public class UserView {
    @JSON(name = "id")
    private Id<User> _id;

    @JSON(name = "handle")
    private String _handle;

    @JSON(name = "createdAt")
    private ZonedDateTime _createdAt;

    @JSON(name = "firstName")
    private String _firstName;

    @JSON(name = "lastName")
    private String _lastName;

    public UserView() {
    }

    private UserView(Id<User> id, String handle, ZonedDateTime createdAt) {
        _id = id;
        _handle = handle;
        _createdAt = createdAt;
    }

    public static UserView fromHibernateEntity(User user) {
        UserView userView = new UserView(user.getId(), user.getHandle(), user.getCreatedAt());
        user.getFirstName().ifPresent(firstName -> userView.setFirstName(firstName));
        user.getLastName().ifPresent(lastName -> userView.setLastName(lastName));
        return userView;
    }

    public Id<User> getId() {
        return _id;
    }

    public void setId(Id<User> id) {
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
