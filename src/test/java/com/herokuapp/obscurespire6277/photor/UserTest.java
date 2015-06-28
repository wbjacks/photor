package com.herokuapp.obscurespire6277.photor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest extends PersistentTestBase {

    @Test
    public void testPersistence() {
        User user = new User();
        session.save(user);

        assertEquals(1, session.createCriteria(User.class).list().size());
    }

}
