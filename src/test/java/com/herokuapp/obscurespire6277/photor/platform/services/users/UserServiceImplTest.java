package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.repos.UserRepositoryService;
import org.easymock.EasyMockSupport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserServiceImplTest extends EasyMockSupport {
    private static final FacebookUserId FACEBOOK_ID = new FacebookUserId("id1");
    private static final FacebookLongToken LONG_TOKEN = new FacebookLongToken("shortToken");
    private static final String HANDLE = "happy_leeland";

    @Test
    public void testSignUpUserThrowsErrorWhenHandleIsAlreadyInUse() {
        UserRepositoryService userRepositoryService = createStrictMock(UserRepositoryService.class);
        expect(userRepositoryService.isHandleAvailable(HANDLE)).andReturn(false).once();

        UserServiceImpl userServiceImpl = new Builder().with(userRepositoryService).build();
        replayAll();
        try {
            userServiceImpl.signUpUser(null, null, HANDLE);
        } catch (UserHandleIsNotAvailableException e) {
            verifyAll();
            return;
        }
        fail("signUpUser should throw exception.");
    }

    @Test
    public void testSignUpUserCreatesUserWhenHandleIsAvailabile() throws UserHandleIsNotAvailableException {
        UserRepositoryService userRepositoryService = createStrictMock(UserRepositoryService.class);
        expect(userRepositoryService.isHandleAvailable(HANDLE)).andReturn(true).once();
        expect(userRepositoryService.createUserFromFacebookData(eq(FACEBOOK_ID), eq(LONG_TOKEN), eq(HANDLE))).andReturn(null).once();

        UserServiceImpl userServiceImpl = new Builder().with(userRepositoryService).build();

        replayAll();
        userServiceImpl.signUpUser(FACEBOOK_ID, LONG_TOKEN, HANDLE);
        verifyAll();
    }

    @Test
    public void testLogInUserReturnsUserWhenPresentInDatabase() throws UserDoesNotExistException {
        Id<User> userId = Id.of(1);
        UserView userView = UserView.emptyUserView();

        UserRepositoryService userRepositoryService = createStrictMock(UserRepositoryService.class);
        expect(userRepositoryService.saveUserLoginAndUpdateToken(FACEBOOK_ID, LONG_TOKEN)).andReturn(userId);
        expect(userRepositoryService.getUser(userId)).andReturn(userView);
        replay(userRepositoryService);

        UserView testUser = new Builder().with(userRepositoryService).build().logInUser(FACEBOOK_ID, LONG_TOKEN);

        assertEquals(userView, testUser);
        verify(userRepositoryService);
    }

    @Test
    public void testSignUpUserFillsDataFromFacebook() {
        // TODO: (wbjacks) test
    }

    private class Builder {
        private UserRepositoryService _userRepositoryService = null;
        private final List<String> _fields = new ArrayList<>();

        private Builder() {
        }

        public UserServiceImpl build() {
            return createMockBuilder(UserServiceImpl.class)
                    .addMockedMethods(_fields.toArray(new String[_fields.size()]))
                    .withConstructor(UserRepositoryService.class)
                    .withArgs(_userRepositoryService)
                    .createMock();
        }

        public Builder with(UserRepositoryService userRepositoryService) {
            _userRepositoryService = userRepositoryService;
            return this;
        }

        public Builder withMockedField(String field) {
            _fields.add(field);
            return this;
        }
    }
}
