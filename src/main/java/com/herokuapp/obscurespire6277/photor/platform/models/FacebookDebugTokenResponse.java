package com.herokuapp.obscurespire6277.photor.platform.models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

// TODO: (wbjacks) move me to an inner class?
public class FacebookDebugTokenResponse {
    private String _appId;
    private ZonedDateTime _expiresAt;
    private boolean _isValid;
    private String _userId;

    // The below fields are unneeded
    private String application;
    private Date issuedAt;
    private Map<String, String> metadata;
    private List<String> scopes;

    public static Deserializer getDeserializer() {
        return new Deserializer();
    }

    public String getAppId() {
        return _appId;
    }

    public void setAppId(String appId) {
        this._appId = appId;
    }

    public ZonedDateTime getExpiresAt() {
        return _expiresAt;
    }

    public void setExpiresAt(ZonedDateTime _expiresAt) {
        this._expiresAt = _expiresAt;
    }

    public boolean isValid() {
        return _isValid;
    }

    public void setIsValid(boolean isValid) {
        this._isValid = isValid;
    }

    public String getUserId() {
        return _userId;
    }

    public void setUserId(String _userId) {
        this._userId = _userId;
    }

    private static class Deserializer implements JsonDeserializer<FacebookDebugTokenResponse> {
        @Override
        public FacebookDebugTokenResponse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                throws JsonParseException {
            FacebookDebugTokenResponse facebookDebugTokenResponse = new FacebookDebugTokenResponse();
            JsonObject jsonObject = json.getAsJsonObject();
            facebookDebugTokenResponse.setAppId(jsonObject.get("app_id").getAsString());
            facebookDebugTokenResponse.setExpiresAt(ZonedDateTime.ofInstant(
                    Instant.ofEpochSecond(jsonObject.get("expires_at").getAsLong()), ZoneId.of("PST", ZoneId.SHORT_IDS)));
            facebookDebugTokenResponse.setIsValid(jsonObject.get("is_valid").getAsBoolean());
            facebookDebugTokenResponse.setUserId(jsonObject.get("user_id").getAsString());
            return facebookDebugTokenResponse;
        }
    }
}
