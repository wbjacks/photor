package com.herokuapp.obscurespire6277.photor.platform.web.controller;

import com.herokuapp.obscurespire6277.photor.platform.web.util.Route;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInitMethod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class BaseController {
    @PetiteInitMethod
    public void registerController() {
        for (Method method : this.getClass().getMethods()) {
            if (method.isAnnotationPresent(Route.class)) {
                try {
                    method.invoke(this);
                }
                catch(Exception e) {
                }
            }
        }
    }
}