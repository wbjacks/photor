package com.herokuapp.obscurespire6277.photor;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.services.users.FacebookAuthService;
import com.herokuapp.obscurespire6277.photor.util.ioc.ServiceManager;
import spark.Spark;

public class Application {
    /* Responsible for startup and global initialization */
    public static void main(String[] args) {
        if (args.length == 1) {
            Spark.port(Integer.valueOf(args[0]));
        }
        ServiceManager.registerServices();

        // Global configurations

        // Security filters
        Spark.before((request, response) -> {
            if (!ServiceManager.getBean(FacebookAuthService.class).isUserAuthenticatedWithToken
                (new FacebookUserId(request.headers("user_id")), new FacebookLongToken(request.headers("token"))))
            {
                Spark.halt(403, "Authentication rejected."); // idk w/e
            }
        });
    }
}