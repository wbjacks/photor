package com.herokuapp.obscurespire6277.photor.platform.web.controller;

import com.herokuapp.obscurespire6277.photor.FooService;
import com.herokuapp.obscurespire6277.photor.platform.web.util.Route;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInitMethod;
import jodd.petite.meta.PetiteInject;
import spark.Spark;

@PetiteBean
public class FooController extends BaseController {
    FooService _fooService;

    @PetiteInject
    public FooController(FooService fooService) {
        _fooService = fooService;
    }

    @Route
    public void getRoot() {
        Spark.get("/", (req, resp) -> _fooService.printFoo() + " " + _fooService.printBar());
    }
}
