package com.herokuapp.obscurespire6277.photor.platform.web.controller;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.Route;
import jodd.json.JsonSerializer;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import spark.Spark;

@PetiteBean
public class UserController extends BaseController {
    private static final String JSON_MIME_TYPE = "application/json";
    private final UserService _userService;

    @PetiteInject
    public UserController(UserService userService) {
        _userService = userService;
    }

    @Route
    public void logInUser() {
        Spark.get("/user/:user_id/login/:short_token", ((request, response) -> {
            UserView userView = _userService.logInUser(Id.<User>of(Long.valueOf(request.params(":user_id"))),
                request.params(":short_token"));
            response.type(JSON_MIME_TYPE);
            return new JsonSerializer().serialize(userView);
        }));
    }
}
