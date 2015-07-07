package com.herokuapp.obscurespire6277.photor.platform.web.controller;

import com.herokuapp.obscurespire6277.photor.platform.web.util.Route;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInitMethod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@PetiteBean
public abstract class BaseController {
    @PetiteInitMethod
    public void registerController() throws IllegalAccessException, InvocationTargetException {
        for (Method method : this.getClass().getMethods()) {
            if (Arrays.asList(method.getAnnotations()).contains(Route.class)) {
                method.invoke(this);
            }
        }
    }
}