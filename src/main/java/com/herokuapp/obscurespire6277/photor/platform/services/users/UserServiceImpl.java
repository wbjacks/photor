package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.UserEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Transactor;
import com.herokuapp.obscurespire6277.photor.platform.models.User;
import com.herokuapp.obscurespire6277.photor.platform.repos.UserRepositoryService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.io.IOException;
import java.util.Optional;

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
    public String logInUser(Id<UserEntity> id, String facebookShortToken) throws IOException, ThirdPartyException {
        String longToken = _facebookAuthService.getLongTokenFromShortToken(facebookShortToken);
        _userRepositoryService.saveTokenToUser(id, longToken);
        return longToken;
    }
}
