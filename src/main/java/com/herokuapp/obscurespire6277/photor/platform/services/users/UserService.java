package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.util.web.WebCallException;
import org.apache.http.auth.AuthenticationException;

public interface UserService {
    UserView logInUser(String facebookUserId, String facebookShortToken) throws AuthenticationException;

    UserView signUpUser(String facebookUserId, String facebookShortToken);
}
