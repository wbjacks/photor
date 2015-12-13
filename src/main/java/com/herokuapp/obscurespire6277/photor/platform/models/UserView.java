package com.herokuapp.obscurespire6277.photor.platform.models;

import com.google.gson.*;
import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.util.Immutable;
import com.herokuapp.obscurespire6277.photor.util.JsonEntity;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.Optional;

@JsonEntity
@Immutable
public class UserView extends BaseJsonEntity {
    private Id<User> _id;
    private String _handle;
    private ZonedDateTime _createdAt;
    private String _firstName;
    private String _lastName;

    private UserView() {
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

    public static Serializer getSerializer() {
        return new Serializer();
    }

    public static UserView emptyUserView() {
        return new UserView();
    }

    public Id<User> getId() {
        return _id;
    }

    private void setId(Id<User> id) {
        _id = id;
    }

    public String getHandle() {
        return _handle;
    }

    private void setHandle(String handle) {
        _handle = handle;
    }

    public ZonedDateTime getCreatedAt() {
        return _createdAt;
    }

    private void setCreatedAt(ZonedDateTime createdAt) {
        _createdAt = createdAt;
    }

    public Optional<String> getFirstName() {
        return Optional.ofNullable(_firstName);
    }

    private void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public Optional<String> getLastName() {
        return Optional.ofNullable(_lastName);
    }

    private void setLastName(String lastName) {
        _lastName = lastName;
    }
    //private List<LogIn> logins = new ArrayList<>();
    //private List<Photo> photos = new ArrayList<>();
    //private List<Like> likes = new ArrayList<>();
    //private List<Comment> comments = new ArrayList<>();

    private static class Serializer implements JsonSerializer<UserView> {
        @Override
        public JsonElement serialize(UserView userView, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("id", new JsonPrimitive(userView.getId().toLong()));
            jsonObject.add("handle", new JsonPrimitive(userView.getHandle()));
            userView.getFirstName().ifPresent(firstName -> jsonObject.add("firstName", new JsonPrimitive(firstName)));
            userView.getLastName().ifPresent(lastName -> jsonObject.add("lastName", new JsonPrimitive(lastName)));
            return jsonObject;
        }
    }
}
