package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.repos.UserRepositoryService;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

@PetiteBean("userService")
public class UserServiceImpl implements UserService {
    private final UserRepositoryService _userRepositoryService;

    @PetiteInject
    public UserServiceImpl(UserRepositoryService userRepositoryService) {
        _userRepositoryService = userRepositoryService;
    }

    @Override
    public UserView logInUser(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) throws UserDoesNotExistException {
        Id<User> userId = _userRepositoryService.saveUserLoginAndUpdateToken(facebookUserId, facebookLongToken);
        return _userRepositoryService.getUser(userId);
    }

    @Override
    public UserView signUpUser(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken, String handle) throws UserHandleIsNotAvailableException {
        if (isHandleAvailable(handle)) {
            // TODO: (wbjacks) fill out user data from fbook request
            return _userRepositoryService.createUserFromFacebookData(facebookUserId, facebookLongToken, handle);
        }
        else {
            throw new UserHandleIsNotAvailableException(String.format("Sign in attempt for existing user handle [%s].", handle));
        }
    }

    @Override
    public boolean isUserSignedUp(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) {
        return _userRepositoryService.isUserSignedUp(facebookUserId, facebookLongToken);
    }

    @Override
    public boolean isHandleAvailable(String handle) {
        return _userRepositoryService.isHandleAvailable(handle);
    }
}