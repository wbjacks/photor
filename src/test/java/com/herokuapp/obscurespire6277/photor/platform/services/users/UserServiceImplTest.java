package com.herokuapp.obscurespire6277.photor.platform.services.users;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.repos.UserRepositoryService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class UserServiceImplTest {
    private static final FacebookUserId FACEBOOK_ID = new FacebookUserId("id1");
    private static final FacebookLongToken SHORT_TOKEN = new FacebookLongToken("shortToken");

    @Test
    public void testLogInUserReturnsUserWhenPresentInDatabase() throws UserDoesNotExistException {
        Id<User> userId = Id.of(1);
        UserView userView = UserView.emptyUserView();

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
