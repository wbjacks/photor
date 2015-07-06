package com.herokuapp.obscurespire6277.photor;

import com.herokuapp.obscurespire6277.photor.util.ioc.PetiteManager;
import spark.Spark;

public class Application {
    public static void main(String[] args) {
        if (args.length == 1) {
            Spark.port(Integer.valueOf(args[0]));
        }
        PetiteManager.registerServices();

        FooService fooService = PetiteManager.getBean(FooService.class);

        Spark.get("/", (req, resp) -> fooService.printFoo() + " " + fooService.printBar());
    }
}