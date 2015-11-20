package com.herokuapp.obscurespire6277.photor.platform.models;

import com.herokuapp.obscurespire6277.photor.util.Immutable;

@Immutable
public class FacebookUserId {
    private final String _id;

    public FacebookUserId(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FacebookUserId && this._id.equals(((FacebookUserId) o).getId());
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}
