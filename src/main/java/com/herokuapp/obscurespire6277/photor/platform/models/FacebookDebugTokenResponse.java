package com.herokuapp.obscurespire6277.photor.platform.models;

import com.google.gson.*;
import com.herokuapp.obscurespire6277.photor.util.Immutable;
import com.herokuapp.obscurespire6277.photor.util.JsonEntity;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Immutable
public class FacebookDebugTokenResponse extends BaseJsonEntity {
    private String _appId;
    private ZonedDateTime _expiresAt;
    private boolean _isValid;
    private FacebookUserId _facebookUserId;

    public static FacebookDebugTokenResponse buildForTestingWithAllValues(String appId, ZonedDateTime expiresAt, boolean isValid, FacebookUserId facebookUserId) {
        return new FacebookDebugTokenResponse(appId, expiresAt, isValid, facebookUserId);
    }

    public static Deserializer getDeserializer() {
        return new Deserializer();
    }

    private FacebookDebugTokenResponse() {
    }

    private FacebookDebugTokenResponse(String appId, ZonedDateTime expiresAt, boolean isValid, FacebookUserId facebookUserId) {
        _appId = appId;
        _expiresAt = expiresAt;
        _isValid = isValid;
        _facebookUserId = facebookUserId;
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

    public FacebookUserId getFacebookUserId() {
        return _facebookUserId;
    }

    private void setFacebookUserId(FacebookUserId facebookUserId) {
        _facebookUserId = facebookUserId;
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
            facebookDebugTokenResponse.setFacebookUserId(new FacebookUserId(jsonObject.get
            ("user_id").getAsString()));
            return facebookDebugTokenResponse;
        }
    }
}
