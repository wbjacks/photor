package com.herokuapp.obscurespire6277.photor.entities;

import com.herokuapp.obscurespire6277.photor.PersistentTestBase;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.DbSession;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.WithSession;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.WithVoidReadOnlySession;
import org.junit.Test;

import java.time.ZonedDateTime;

import static com.herokuapp.obscurespire6277.photor.util.time.TimeZones.ET;

public class UserTest extends PersistentTestBase {

    @Test
    public void testPersistence() {
        User user = new User("amith", ZonedDateTime.now(ET));
        Id<User> userId = _transactor.execute(new WithSession<Id<User>>() {
            @Override
            public Id<User> run(DbSession session) {
                return session.save(user);
            }
        });
        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(DbSession readOnlySession) {
                User user = readOnlySession.get(User.class, userId);
            }
        });
    }

}
