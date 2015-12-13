package com.herokuapp.obscurespire6277.photor.platform.web.controller;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.models.json.UserRequest;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserDoesNotExistException;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserService;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.SerializationUtilService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.Route;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import spark.Spark;

@PetiteBean
public class UserController extends BaseController {
    private static final String JSON_MIME_TYPE = "application/json";
    private final SerializationUtilService _serializationUtilService;
    private final UserService _userService;

    @PetiteInject
    public UserController(SerializationUtilService serializationUtilService, UserService userService) {
        _serializationUtilService = serializationUtilService;
        _userService = userService;
    }

    @Route
    @SuppressWarnings("unused")
    public void logInUser() {
        Spark.post("/loginUser", ((request, response) -> {
            UserRequest userRequest = deserializeUserRequest(request.body());
            response.type(JSON_MIME_TYPE);
            try {
                UserView userView = _userService.logInUser(userRequest.getFacebookUserId().get(), userRequest.getFacebookLongToken().get());
                return _serializationUtilService.serializeObjectToJson(userView);
            }
            catch (UserDoesNotExistException e) {
                response.status(401);
                return "User not found.";
            }
        }));
    }

    @Route
    @SuppressWarnings("unused")
    public void signUpUser() {
        Spark.post("/signUp", (request, response) -> {
            UserRequest userRequest = deserializeUserRequest(request.body());
            // TODO: (wbjacks) handle error better?
            UserView userView = _userService.signUpUser(userRequest.getFacebookUserId().get(), userRequest.getFacebookLongToken()
                    .get(), userRequest.getHandle().get());
            response.type(JSON_MIME_TYPE);
            return _serializationUtilService.serializeObjectToJson(userView);
        });
    }

    @Route
    @SuppressWarnings("unused")
    public void checkUserSignedUp() {
        Spark.get("/checkUserSignedUp", (request, response) ->
                _userService.isUserSignedUp(new FacebookUserId(request.params("user_id")),
                        new FacebookLongToken(request.params("token")))
        );
    }

    @Route
    @SuppressWarnings("unused")
    public void checkHandleAvailable() {
        Spark.get("/checkHandleAvailable", (request, response) ->
                _userService.isHandleAvailable(request.params("handle"))
        );
    }

    private UserRequest deserializeUserRequest(String json) {
        return _serializationUtilService.parseJsonToObject(json, UserRequest.class);
    }
}