package com.herokuapp.obscurespire6277.photor;

import org.junit.Test;
import static org.junit.Assert.*;

public class FooServiceTest {
    @Test
    public void testPrintFooPrintsFoo() {
        FooService fooService = new FooService(null);
        assertEquals("Foo!", fooService.printFoo());
    }
}
