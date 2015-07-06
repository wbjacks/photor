package com.herokuapp.obscurespire6277.photor;

import jodd.petite.PetiteContainer;
import static spark.Spark;

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
