package com.herokuapp.obscurespire6277.photor.platform.web.controller;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserService;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.SerializationUtilService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.Route;
import jodd.json.JsonSerializer;
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
        Spark.get("/user/:facebook_user_id/login/:token", ((request, response) -> {
            UserView userView = _userService.logInUser(new FacebookUserId(request.params(":facebook_user_id")),
                    new FacebookLongToken(request.params(":token")));
            response.type(JSON_MIME_TYPE);
            return _serializationUtilService.serializeObjectToJson(userView);
        }));
    }
}