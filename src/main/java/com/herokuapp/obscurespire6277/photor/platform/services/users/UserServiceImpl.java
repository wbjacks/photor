package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.platform.models.User;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.Optional;

@PetiteBean("userService")
public class UserServiceImpl implements UserService {
    private final FacebookAuthService _facebookAuthService;

    @PetiteInject
    public UserServiceImpl(FacebookAuthService facebookAuthService) {
        _facebookAuthService = facebookAuthService;
    }

    @Override
    public Optional<String> logInUser(String userId, String facebookShortToken) {
        Optional<String> longToken = _facebookAuthService.getLongTokenFromShortToken(facebookShortToken);

        // If token is returned, store to database
        if (longToken.isPresent()) {

        }
        return longToken;
    }
}
