package com.herokuapp.obscurespire6277.photor;

import org.junit.Test;

import java.time.ZonedDateTime;

import static com.herokuapp.obscurespire6277.photor.util.time.TimeZones.ET;
import static org.junit.Assert.assertEquals;

public class UserTest extends PersistentTestBase {

    @Test
    public void testPersistence() {
        ZonedDateTime now = ZonedDateTime.now(ET);
        session.save(new User("amith", now));

        User user = (User) session.createCriteria(User.class).list().get(0);
        assertEquals("amith", user.getHandle());
        assertEquals(now, user.getCreatedAt());
    }

}
