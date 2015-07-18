package com.herokuapp.obscurespire6277.photor.util.ioc;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceManagerTest {
    @Test
    public void testRegisteredServiceCanBeFetchedFromPetite() {
        ServiceManager.CORE_SERVICES.clear();
        ServiceManager.WEB_SERVICES.clear();
        ServiceManager.CORE_SERVICES.add(TestClass.class);
        ServiceManager.registerServices();

        assertNotNull(ServiceManager.PETITE_CONTAINER.getBean(TestClass.class));
    }
}
