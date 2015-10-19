package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;

import java.io.IOException;
import java.util.Optional;

public interface FacebookAuthService {
    boolean isUserAuthenticatedWithToken(String userId, String token);

    String getLongTokenFromShortToken(String shortToken) throws IOException, ThirdPartyException;
}
