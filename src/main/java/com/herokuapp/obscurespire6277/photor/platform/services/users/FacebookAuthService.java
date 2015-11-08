package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.platform.web.util.WebCallException;

public interface FacebookAuthService {
    boolean isUserAuthenticatedWithToken(String userId, String token);

    String getLongTokenFromShortToken(String shortToken) throws WebCallException, ThirdPartyException;
}
