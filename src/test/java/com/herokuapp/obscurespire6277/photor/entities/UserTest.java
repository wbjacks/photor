package com.herokuapp.obscurespire6277.photor.entities;

import com.herokuapp.obscurespire6277.photor.PersistentTestBase;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.TypeSafeSessionWrapper;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.WithSession;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.WithVoidReadOnlySession;
import org.junit.Test;

import java.time.ZonedDateTime;

import static com.herokuapp.obscurespire6277.photor.util.time.TimeZones.ET;
import static org.junit.Assert.assertEquals;

public class UserTest extends PersistentTestBase {

    @Test
    public void testPersistence() {
        User user = new User("amith", ZonedDateTime.now(ET));
        Id<User> userId = _transactor.execute(new WithSession<Id<User>>() {
            @Override
            public Id<User> run(TypeSafeSessionWrapper session) {
                return session.save(user);
            }
        });
        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(TypeSafeSessionWrapper readOnlySession) {
                User user = readOnlySession.get(User.class, userId).get();
                assertEquals("amith", user.getHandle());
            }
        });
    }

}
