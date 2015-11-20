package com.herokuapp.obscurespire6277.photor.util.ioc;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.Transactor;
import com.herokuapp.obscurespire6277.photor.platform.repos.UserRepositoryService;
import com.herokuapp.obscurespire6277.photor.platform.services.users.FacebookAuthService;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserService;
import com.herokuapp.obscurespire6277.photor.platform.web.controller.UserController;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.SerializationUtilService;
import com.herokuapp.obscurespire6277.photor.platform.services.util.crypto.CryptoService;
import com.herokuapp.obscurespire6277.photor.platform.services.util.web.WebCallService;
import jodd.petite.PetiteContainer;
import jodd.petite.meta.InitMethodInvocationStrategy;
import jodd.util.StringUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class ServiceManager {
    private static final Logger _logger = Logger.getLogger(ServiceManager.class);
    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    static final List<Class> CORE_SERVICES = new ArrayList<>();
    static final List<Class> WEB_SERVICES = new ArrayList<>();
    static final List<Class> WEB_CONTROLLERS = new ArrayList<>();

    static final PetiteContainer PETITE_CONTAINER = new PetiteContainer();

    static {
        CORE_SERVICES.add(UserService.class);
        CORE_SERVICES.add(WebCallService.class);
        CORE_SERVICES.add(CryptoService.class);
        CORE_SERVICES.add(UserRepositoryService.class);
        CORE_SERVICES.add(Transactor.class);

        WEB_SERVICES.add(FacebookAuthService.class);
        WEB_SERVICES.add(SerializationUtilService.class);

        WEB_CONTROLLERS.add(UserController.class);

        PETITE_CONTAINER.getConfig().setDetectDuplicatedBeanNames(true);
    }

    private ServiceManager() {
        throw new IllegalStateException();
    }

    public static void registerServices() {
        for (Class coreService : CORE_SERVICES) {
            PETITE_CONTAINER.registerPetiteBean(getImplementationForInterface(coreService), null, null, null, false);
        }
        for (Class webService : WEB_SERVICES) {
            PETITE_CONTAINER.registerPetiteBean(getImplementationForInterface(webService), null, null, null, false);
        }
        for (Class webController : WEB_CONTROLLERS) {
            PETITE_CONTAINER.registerPetiteBean(getImplementationForInterface(webController), null, null, null, false);
        }
        forceControllerWiring();
    }

    // TODO: (wbjacks) reflection pls
    private static Class getImplementationForInterface(Class interfaceClass) {
        try {
            return Class.forName(interfaceClass.getName() + IMPLEMENTATION_SUFFIX);
        } catch (ClassNotFoundException e) {
            _logger.error(String.format("Error finding implementation class for %s, using interface for registration.",
                    interfaceClass.getSimpleName()));
            return interfaceClass;
        }
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
