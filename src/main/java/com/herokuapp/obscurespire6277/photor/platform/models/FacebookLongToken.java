package com.herokuapp.obscurespire6277.photor.platform.models;

import com.herokuapp.obscurespire6277.photor.util.Immutable;

@Immutable
public class FacebookLongToken {
    private final String _token;

    public FacebookLongToken(String token) {
        _token = token;
    }

    public String getToken() {
        return _token;
    }
}