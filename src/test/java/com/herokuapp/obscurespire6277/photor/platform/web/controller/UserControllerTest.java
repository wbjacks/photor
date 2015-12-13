package com.herokuapp.obscurespire6277.photor.platform.web.controller;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.json.UserRequest;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserDoesNotExistException;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserService;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.SerializationUtilService;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class UserControllerTest extends EasyMockSupport {
    private static final FacebookUserId FACEBOOK_ID = new FacebookUserId("id1");
    private static final FacebookLongToken LONG_TOKEN = new FacebookLongToken("shortToken");
    private static final String HANDLE = "happy_leeland";

    @Test
    public void testLoginUserReturns401ErrorIfUserServiceThrowsError() throws UserDoesNotExistException {
        UserRequest userRequest = new UserRequest(FACEBOOK_ID, LONG_TOKEN, HANDLE);

        Request request = createStrictMock(Request.class);
        expect(request.body()).andReturn("").once();

        Response response = createStrictMock(Response.class);
        response.type("application/json");
        expectLastCall().once();
        response.status(401);
        expectLastCall().once();

        SerializationUtilService serializationUtilService = createStrictMock(SerializationUtilService.class);
        expect(serializationUtilService.parseJsonToObject("", UserRequest.class)).andReturn(userRequest);

        UserService userService = createStrictMock(UserService.class);
        expect(userService.logInUser(FACEBOOK_ID, LONG_TOKEN)).andThrow(new UserDoesNotExistException(""));

        replayAll();

        UserController userController = new UserController(serializationUtilService, userService);
        assertEquals("User not found.", userController.logInUser(request, response));
        verifyAll();
    }
}