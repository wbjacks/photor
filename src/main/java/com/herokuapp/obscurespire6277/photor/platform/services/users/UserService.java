package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.UserEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;

import java.io.IOException;

public interface UserService {
    String logInUser(Id<UserEntity> id, String facebookShortToken) throws IOException, ThirdPartyException;
}
