package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;

public interface FacebookAuthService {
    boolean isUserAuthenticatedWithToken(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken);
}
