package com.herokuapp.obscurespire6277.photor.entities;

import com.herokuapp.obscurespire6277.photor.PersistentTestBase;
import com.herokuapp.obscurespire6277.photor.entities.User;
import org.junit.Test;

import java.time.ZonedDateTime;

import static com.herokuapp.obscurespire6277.photor.util.time.TimeZones.ET;
import static org.junit.Assert.assertEquals;

public class UserTest extends PersistentTestBase {

    @Test
    public void testPersistence() {
        ZonedDateTime now = ZonedDateTime.now(ET);
        long id = (Long) session.save(new User("amith", now));

        User user = (User) session.get(User.class, id);
        assertEquals("amith", user.getHandle());
        assertEquals(now, user.getCreatedAt());
    }

}
