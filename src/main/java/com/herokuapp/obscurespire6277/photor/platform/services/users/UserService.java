package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.UserEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.User;
import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.util.web.WebCallException;

import java.io.IOException;

public interface UserService {
    User logInUser(Id<UserEntity> id, String facebookShortToken) throws WebCallException, ThirdPartyException;
}
