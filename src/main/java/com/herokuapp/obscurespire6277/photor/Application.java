package com.herokuapp.obscurespire6277.photor;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.services.users.FacebookAuthService;
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
        JoddJson.defaultSerializers.register(Id.class, (jsonContext, value) -> jsonContext
            .writeString(value.toString()));

        // Security filters
        Spark.before((request, response) -> {
            if (!ServiceManager.getBean(FacebookAuthService.class).isUserAuthenticatedWithToken
                (request.headers("user_id"), request.headers("token")))
            {
                response.redirect("/login");
            }
        });
    }
}