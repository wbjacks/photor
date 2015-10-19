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
        UserEntity userEntity = new UserEntity("amith", ZonedDateTime.now(ET));
        Id<UserEntity> userId = _transactor.execute(new WithSession<Id<UserEntity>>() {
            @Override
            public Id<UserEntity> run(TypeSafeSessionWrapper session) {
                return session.save(userEntity);
            }
        });
        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(TypeSafeSessionWrapper readOnlySession) {
                UserEntity userEntity = readOnlySession.get(UserEntity.class, userId).get();
                assertEquals("amith", userEntity.getHandle());
            }
        });
    }
}
