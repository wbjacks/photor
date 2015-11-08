package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import org.apache.http.auth.AuthenticationException;

public interface UserService {
    UserView logInUser(String facebookUserId, String facebookShortToken) throws AuthenticationException;

    UserView signUpUser(String facebookUserId, String facebookShortToken);
}
