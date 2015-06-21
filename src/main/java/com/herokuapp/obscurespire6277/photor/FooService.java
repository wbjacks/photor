package com.herokuapp.obscurespire6277.photor;

import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

@PetiteBean
public class FooService {
    BarService _barService;

    @PetiteInject
    public FooService(BarService barService) {
        _barService = barService;
    }

    public String printFoo() {
        return "Foo!";
    }

    public String printBar() {
        return _barService.printBar();
    }
}
