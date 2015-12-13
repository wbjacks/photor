package com.herokuapp.obscurespire6277.photor.platform.models.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.herokuapp.obscurespire6277.photor.platform.models.BaseJsonEntity;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.util.Immutable;

import java.lang.reflect.Type;
import java.util.Optional;

@Immutable
public class UserRequest extends BaseJsonEntity {
    private FacebookUserId _facebookUserId;
    private FacebookLongToken _facebookLongToken;
    private String _handle;

    public static Deserializer getDeserializer() {
        return new Deserializer();
    }

    private UserRequest() {
    }

    public UserRequest(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken, String handle) {
        _facebookLongToken = facebookLongToken;
        _facebookUserId = facebookUserId;
        _handle = handle;
    }

    public Optional<FacebookUserId> getFacebookUserId() {
        return Optional.ofNullable(_facebookUserId);
    }

    private void setFacebookUserId(FacebookUserId facebookUserId) {
        _facebookUserId = facebookUserId;
    }

    public Optional<FacebookLongToken> getFacebookLongToken() {
        return Optional.ofNullable(_facebookLongToken);
    }

    private void setFacebookLongToken(FacebookLongToken facebookLongToken) {
        _facebookLongToken = facebookLongToken;
    }

    public Optional<String> getHandle() {
        return Optional.ofNullable(_handle);
    }

    private void setHandle(String handle) {
        _handle = handle;
    }


    private static class Deserializer implements JsonDeserializer<UserRequest> {
        @Override
        public UserRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            UserRequest userRequest = new UserRequest();
            JsonElement facebookUserIdString = json.getAsJsonObject().get("user_id");
            if (facebookUserIdString != null) {
                userRequest.setFacebookUserId(new FacebookUserId(facebookUserIdString.getAsString()));
            }

            JsonElement facebookLongTokenString = json.getAsJsonObject().get("token");
            if (facebookLongTokenString != null) {
                userRequest.setFacebookLongToken(new FacebookLongToken(facebookLongTokenString.getAsString()));
            }

            JsonElement handle = json.getAsJsonObject().get("handle");
            if (handle != null) {
                userRequest.setHandle(handle.getAsString());
            }
            return userRequest;
        }
    }
}
