package com.herokuapp.obscurespire6277.photor.platform.services.users;

import java.util.Optional;

public interface FacebookAuthService {
    boolean isUserAuthenticatedWithToken(String userId, String token);

    Optional<String> getLongTokenFromShortToken(String shortToken);
}
