package com.herokuapp.obscurespire6277.photor;

import jodd.petite.PetiteContainer;
import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        if (args.length == 1) {
            port(Integer.valueOf(args[0]));
        }
        PetiteContainer petiteContainer = new PetiteContainer();
        petiteContainer.registerPetiteBean(FooService.class, null, null, null, false);
        petiteContainer.registerPetiteBean(BarService.class, null, null, null, false);

        FooService fooService = (FooService)petiteContainer.getBean("fooService");

        get("/", (req, resp) -> fooService.printFoo() + " " + fooService.printBar());
    }
}
