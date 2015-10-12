package com.herokuapp.obscurespire6277.photor.util.ioc;

import com.herokuapp.obscurespire6277.photor.platform.services.users.FacebookAuthService;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserService;
import com.herokuapp.obscurespire6277.photor.platform.web.controller.UserController;
import com.herokuapp.obscurespire6277.photor.util.web.WebCallService;
import jodd.petite.PetiteContainer;
import jodd.petite.meta.InitMethodInvocationStrategy;
import jodd.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public final class ServiceManager {
    static final List<Class> CORE_SERVICES = new ArrayList<>();
    static final List<Class> WEB_SERVICES = new ArrayList<>();
    static final List<Class> WEB_CONTROLLERS = new ArrayList<>();

    static final PetiteContainer PETITE_CONTAINER = new PetiteContainer();

    static {
        CORE_SERVICES.add(UserService.class);
        CORE_SERVICES.add(WebCallService.class);

        WEB_SERVICES.add(FacebookAuthService.class);

        WEB_CONTROLLERS.add(UserController.class);

        PETITE_CONTAINER.getConfig().setDetectDuplicatedBeanNames(true);
    }

    private ServiceManager() {
        throw new IllegalStateException();
    }

    public static void registerServices() {
        for (Class coreService : CORE_SERVICES) {
            PETITE_CONTAINER.registerPetiteBean(coreService, null, null, null, false);
        }
        for (Class webService : WEB_SERVICES) {
            PETITE_CONTAINER.registerPetiteBean(webService, null, null, null, false);
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
