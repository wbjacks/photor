package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.User;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

@PetiteBean("userService")
public class UserServiceImpl implements UserService {
    private final FacebookAuthService _facebookAuthService;

    @PetiteInject
    public UserServiceImpl(FacebookAuthService facebookAuthService) {
        _facebookAuthService = facebookAuthService;
    }

    public User getUser(String userId) {
        // Do DB fetch
        User user = new User();
        // If user doesn't exist in DB, throw error
        // Auth with facebook
        // return
        return user;
    }

    public void logInUser(String facebookShortToken) {

    }
}
