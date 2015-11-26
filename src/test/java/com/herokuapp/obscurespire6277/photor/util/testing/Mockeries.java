package com.herokuapp.obscurespire6277.photor.util.testing;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

public class Mockeries {

    public static Mockery mockery() {
        return mockery(false);
    }

    public static Mockery mockery(boolean useClassImposteriser) {
        JUnit4Mockery mockery = new JUnit4Mockery();
        if (useClassImposteriser) {
            mockery.setImposteriser(ClassImposteriser.INSTANCE);
        }
        return mockery;
    }

}
