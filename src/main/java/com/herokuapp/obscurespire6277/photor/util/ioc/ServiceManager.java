package com.herokuapp.obscurespire6277.photor.util.ioc;

import com.herokuapp.obscurespire6277.photor.BarService;
import com.herokuapp.obscurespire6277.photor.FooService;
import com.herokuapp.obscurespire6277.photor.platform.web.controller.FooController;
import jodd.petite.PetiteContainer;
import jodd.petite.meta.InitMethodInvocationStrategy;
import jodd.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    static final List<Class> CORE_SERVICES = new ArrayList<>();
    static final List<Class> WEB_SERVICES = new ArrayList<>();
    static final List<Class> WEB_CONTROLLERS = new ArrayList<>();

    static final PetiteContainer PETITE_CONTAINER = new PetiteContainer();

    static {
        CORE_SERVICES.add(FooService.class);
        CORE_SERVICES.add(BarService.class);

        WEB_CONTROLLERS.add(FooController.class);

        PETITE_CONTAINER.getConfig().setDetectDuplicatedBeanNames(true);
    }

    public static void registerServices() {
        for (Class coreService : CORE_SERVICES) {
            PETITE_CONTAINER.registerPetiteBean(coreService, null, null, null, false);
        }
        for (Class webController : WEB_CONTROLLERS) {
            PETITE_CONTAINER.registerPetiteBean(webController, null, null, null, false);
        }
        forceControllerWiring();
    }

    private static void forceControllerWiring() {
        for (Class webController : WEB_CONTROLLERS) {
            String beanName = StringUtil.uncapitalize(webController.getSimpleName());
            PETITE_CONTAINER.registerPetiteInitMethods(beanName,
                InitMethodInvocationStrategy.POST_INITIALIZE, "registerController");
            getBean(webController);
        }
    }

    public static <T> T getBean(Class<T> cla$$) {
        return PETITE_CONTAINER.getBean(cla$$);
    }
}
