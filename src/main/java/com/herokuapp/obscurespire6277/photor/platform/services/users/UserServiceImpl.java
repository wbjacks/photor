package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.repos.UserRepositoryService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.ThirdPartyException;
import com.herokuapp.obscurespire6277.photor.util.web.WebCallException;
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
    public UserView logInUser(Id<User> id, String facebookShortToken) throws WebCallException, ThirdPartyException {
        String longToken = _facebookAuthService.getLongTokenFromShortToken(facebookShortToken);
        _userRepositoryService.saveTokenToUser(id, longToken);
        return _userRepositoryService.getUserWithId(id).get();
    }
}
