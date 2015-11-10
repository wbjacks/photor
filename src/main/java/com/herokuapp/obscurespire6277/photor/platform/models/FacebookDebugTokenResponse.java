package com.herokuapp.obscurespire6277.photor.platform.models;

import com.google.gson.*;
import org.apache.http.annotation.Immutable;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

// TODO: (wbjacks) move me to an inner class?
// TODO: (wbjacks) find a new annotation?
@Immutable
public class FacebookDebugTokenResponse {
    private String _appId;
    private ZonedDateTime _expiresAt;
    private boolean _isValid;
    private String _userId;

    public static FacebookDebugTokenResponse buildForTestingWithAllValues(String appId, ZonedDateTime expiresAt, boolean isValid, String userId) {
        return new FacebookDebugTokenResponse(appId, expiresAt, isValid, userId);
    }

    public static Deserializer getDeserializer() {
        return new Deserializer();
    }

    private FacebookDebugTokenResponse() {
    }

    private FacebookDebugTokenResponse(String appId, ZonedDateTime expiresAt, boolean isValid, String userId) {
        _appId = appId;
        _expiresAt = expiresAt;
        _isValid = isValid;
        _userId = userId;
    }

    public String getAppId() {
        return _appId;
    }

    private void setAppId(String appId) {
        this._appId = appId;
    }

    // ZonedDateTime is immutable
    public ZonedDateTime getExpiresAt() {
        return _expiresAt;
    }

    private void setExpiresAt(ZonedDateTime _expiresAt) {
        this._expiresAt = _expiresAt;
    }

    public boolean isValid() {
        return _isValid;
    }

    private void setIsValid(boolean isValid) {
        this._isValid = isValid;
    }

    public String getUserId() {
        return _userId;
    }

    private void setUserId(String _userId) {
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
