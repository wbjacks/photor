package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import org.apache.http.auth.AuthenticationException;

public interface UserService {
    UserView logInUser(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) throws UserDoesNotExistException;

    UserView signUpUser(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken, String handle) throws UserHandleIsNotAvailableException;

    boolean isUserSignedUp(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken);

    boolean isHandleAvailable(String handle);
}
