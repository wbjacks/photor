package com.herokuapp.obscurespire6277.photor.util.ioc;

import com.herokuapp.obscurespire6277.photor.BarService;
import com.herokuapp.obscurespire6277.photor.FooService;
import jodd.petite.PetiteContainer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public final class ServiceManager {
    private static final List<Class> CORE_SERVICES = new ArrayList<>();
    private static final List<Class> WEB_SERVICES = new ArrayList<>();

    private static final PetiteContainer PETITE_CONTAINER = new PetiteContainer();

    static {
        CORE_SERVICES.add(FooService.class);
        CORE_SERVICES.add(BarService.class);

        PETITE_CONTAINER.getConfig().setDetectDuplicatedBeanNames(true);

        // TODO: (wbjacks) call registerServices here?
        // registerServices();
    }

    private ServiceManager() {
        throw new NotImplementedException();
    }

    public static void registerServices() {
        for (Class coreService : CORE_SERVICES) {
            PETITE_CONTAINER.registerPetiteBean(coreService, null, null, null, false);
        }
    }

    public static <T> T getBean(Class<T> cla$$) {
        return PETITE_CONTAINER.getBean(cla$$);
    }
}
