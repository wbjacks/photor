package com.herokuapp.obscurespire6277.photor;

import org.jmock.Expectations;
import org.junit.Test;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;


import static org.junit.Assert.*;

public class FooServiceTest {
    @Test
    public void testPrintFooPrintsFoo() {
        FooService fooService = new FooService(null);
        assertEquals("Foo!", fooService.printFoo());
    }

    @Test
    public void testPrintBarPrintsBar() {
        Mockery mockery = new Mockery() {{
            setImposteriser(ClassImposteriser.INSTANCE);
        }};
        final BarService barService = mockery.mock(BarService.class);
        mockery.checking(new Expectations() {{
            oneOf(barService).printBar();
            will(returnValue("BARRRR"));
        }});

        FooService fooService = new FooService(barService);

        assertEquals("BARRRR", fooService.printBar());
        mockery.assertIsSatisfied();
    }
}
