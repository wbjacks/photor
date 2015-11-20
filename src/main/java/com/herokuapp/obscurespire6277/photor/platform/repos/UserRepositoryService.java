package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserDoesNotExistException;

public interface UserRepositoryService {
    Id<User> saveUserLoginAndUpdateToken(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) throws UserDoesNotExistException;

    UserView createUserFromFacebookData(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken);

    UserView getUser(Id<User> userId) throws UserDoesNotExistException;
}