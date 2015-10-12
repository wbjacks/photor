package com.herokuapp.obscurespire6277.photor.platform.models;

import jodd.json.meta.JSON;
import java.util.Date;

@JSON(strict = true)
public class FacebookDebugTokenResponse {
    @JSON
    private String appId;
    @JSON
    private Date expiresAt;
    @JSON
    private boolean isValid;
    @JSON
    private String userId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
