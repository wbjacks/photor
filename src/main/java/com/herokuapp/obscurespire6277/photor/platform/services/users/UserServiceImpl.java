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
    private final FacebookAuthService _facebookAuthService;
    private final UserRepositoryService _userRepositoryService;

    @PetiteInject
    public UserServiceImpl(FacebookAuthService facebookAuthService, UserRepositoryService userRepositoryService) {
        _facebookAuthService = facebookAuthService;
        _userRepositoryService = userRepositoryService;
    }

    @Override
    public UserView logInUser(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) {
        try {
            Id<User> userId = _userRepositoryService.saveUserLoginAndUpdateToken(facebookUserId, facebookLongToken);
            return _userRepositoryService.getUser(userId);
        } catch (UserDoesNotExistException e) {
            // TODO: (wjacks) should this be moved out of an error condition?
            return signUpUser(facebookUserId, facebookLongToken);
        }
    }

    @Override
    public UserView signUpUser(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) {
        return doSignUpUser(facebookUserId, facebookLongToken);
    }

    private UserView doSignUpUser(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) {
        // TODO: (wbjacks) fill out user data from fbook request
        return _userRepositoryService.createUserFromFacebookData(facebookUserId, facebookLongToken);
    }
}