package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserDoesNotExistException;

public interface UserRepositoryService {
    Id<User> saveUserLoginAndUpdateToken(String facebookUserId, String facebookToken) throws UserDoesNotExistException;

    UserView createUserFromFacebookData(String facebookUserId, String facebookToken);

    UserView getUser(Id<User> userId) throws UserDoesNotExistException;
}