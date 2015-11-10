package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.repos.UserRepositoryService;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class UserServiceImplTest {
    private static final String FACEBOOK_ID = "id1";
    private static final String SHORT_TOKEN = "shortToken";

    @Test
    public void testLogInUserTriesToSignUpUserIfFacebookUserIdIsNotInDatabase() throws UserDoesNotExistException {
        UserRepositoryService userRepositoryService = createStrictMock(UserRepositoryService.class);
        expect(userRepositoryService.saveUserLoginAndUpdateToken(FACEBOOK_ID, SHORT_TOKEN))
                .andThrow(new UserDoesNotExistException("")).once();
        expect(userRepositoryService.createUserFromFacebookData(FACEBOOK_ID, SHORT_TOKEN)).andReturn(null).once();
        replay(userRepositoryService);

        new Builder().with(userRepositoryService).build().logInUser(FACEBOOK_ID, SHORT_TOKEN);
        verify(userRepositoryService);
    }

    @Test
    public void testLogInUserReturnsUserWhenPresentInDatabase() throws UserDoesNotExistException {
        Id<User> userId = Id.of(1);
        UserView userView = new UserView();

        UserRepositoryService userRepositoryService = createStrictMock(UserRepositoryService.class);
        expect(userRepositoryService.saveUserLoginAndUpdateToken(FACEBOOK_ID, SHORT_TOKEN)).andReturn(userId);
        expect(userRepositoryService.getUser(userId)).andReturn(userView);
        replay(userRepositoryService);

        UserView testUser = new Builder().with(userRepositoryService).build().logInUser(FACEBOOK_ID, SHORT_TOKEN);

        assertEquals(userView, testUser);
        verify(userRepositoryService);
    }

    @Test
    public void testSignUpUserFillsDataFromFacebook() {
        // TODO: (wbjacks) test
    }

    private static class Builder {
        private FacebookAuthService _facbookAuthService = null;
        private UserRepositoryService _userRepositoryService = null;

        private Builder() {
        }

        public UserServiceImpl build() {
            return new UserServiceImpl(_facbookAuthService, _userRepositoryService);
        }

        public Builder with(FacebookAuthService facebookAuthService) {
            _facbookAuthService = facebookAuthService;
            return this;
        }

        public Builder with(UserRepositoryService userRepositoryService) {
            _userRepositoryService = userRepositoryService;
            return this;
        }
    }
}
