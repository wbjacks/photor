package com.herokuapp.obscurespire6277.photor.platform.web.controller;

import com.herokuapp.obscurespire6277.photor.platform.services.users.UserService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.Route;
import jodd.json.JsonSerializer;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import spark.Spark;

@PetiteBean
public class UserController extends BaseController {
    private final UserService _userService;

    @PetiteInject
    public UserController(UserService userService) {
        _userService = userService;
    }

    @Route
    public void logInUser() {
        Spark.get("/user/:user_id/login/:short_token", ((request, response) -> _userService.logInUser(request.params(":user_id"), request.params(":short_token"))));

    }
}
