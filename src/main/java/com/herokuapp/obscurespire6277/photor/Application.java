package com.herokuapp.obscurespire6277.photor;

import com.herokuapp.obscurespire6277.photor.platform.services.users.FacebookAuthService;
import com.herokuapp.obscurespire6277.photor.platform.web.controller.FooController;
import com.herokuapp.obscurespire6277.photor.util.ioc.ServiceManager;
import jodd.json.JoddJson;
import spark.Spark;

public class Application {
    /* Responsible for startup and global initialization */
    public static void main(String[] args) {
        if (args.length == 1) {
            Spark.port(Integer.valueOf(args[0]));
        }
        ServiceManager.registerServices();

        // Global configurations
        JoddJson.deepSerialization = true;

        // Security filters
        Spark.before((request, response) -> {
            if (!ServiceManager.getBean(FacebookAuthService.class).isUserAuthenticatedWithToken(
                request.params(":user_id"), request.params(":token")))
            {
                response.redirect("/login");
            }
        });
    }
}