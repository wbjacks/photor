package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.repos.UserRepositoryService;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

@PetiteBean("userService")
public class UserServiceImpl implements UserService {
    private final FacebookAuthService _facebookAuthService;
    private final UserRepositoryService _userRepositoryService;

    @PetiteInject
    public UserServiceImpl(FacebookAuthService facebookAuthService, UserRepositoryService userRepositoryService) {
        _facebookAuthService = facebookAuthService;
        _userRepositoryService = userRepositoryService;
    }

    @Override
    public UserView logInUser(String facebookUserId, String facebookShortToken) {
        try {
            Id<User> userId = _userRepositoryService.saveUserLoginAndUpdateToken(facebookUserId, facebookShortToken);
            return _userRepositoryService.getUser(userId);
        } catch (UserDoesNotExistException e) {
            // TODO: (wjacks) should this be moved out of an error condition?
            return signUpUser(facebookUserId, facebookShortToken);
        }
    }

    @Override
    public UserView signUpUser(String facebookUserId, String facebookShortToken) {
        return doSignUpUser(facebookUserId, facebookShortToken);
    }

    private UserView doSignUpUser(String facebookUserId, String facebookShortToken) {
        // TODO: (wbjacks) fill out user data from fbook request
        return _userRepositoryService.createUserFromFacebookData(facebookUserId, facebookShortToken);
    }
}