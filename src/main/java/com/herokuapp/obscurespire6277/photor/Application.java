package com.herokuapp.obscurespire6277.photor;

import com.herokuapp.obscurespire6277.photor.platform.web.controller.FooController;
import com.herokuapp.obscurespire6277.photor.util.ioc.ServiceManager;
import jodd.json.JoddJson;
import spark.Spark;

public class Application {
    public static void main(String[] args) {
        if (args.length == 1) {
            Spark.port(Integer.valueOf(args[0]));
        }
        ServiceManager.registerServices();

        // Global configurations
        JoddJson.deepSerialization = true;
    }
}
