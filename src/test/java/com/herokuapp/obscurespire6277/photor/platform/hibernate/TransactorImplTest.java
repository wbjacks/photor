package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import com.herokuapp.obscurespire6277.photor.PersistentTestBase;
import com.herokuapp.obscurespire6277.photor.entities.User;
import org.junit.Test;

import java.time.ZonedDateTime;

import static com.herokuapp.obscurespire6277.photor.util.time.TimeZones.PT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TransactorImplTest extends PersistentTestBase {

    @Test
    public void save_persistsAndReturnsIdOfEntity() {
        final Id<User> testUserId = _transactor.save(new User("test", ZonedDateTime.now(PT)));
        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(TypeSafeSessionWrapper readOnlySession) {
                User testUser = readOnlySession.getOrThrow(User.class, testUserId);
                assertEquals("test", testUser.getHandle());
            }
        });
    }

    @Test
    public void execute_withReadOnlySession_doesNotUpdateDb() {
        final Id<User> testUserId = _transactor.save(new User("test", ZonedDateTime.now(PT)));
        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(TypeSafeSessionWrapper readOnlySession) {
                readOnlySession.getOrThrow(User.class, testUserId).setHandle("not test");
            }
        });
        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(TypeSafeSessionWrapper readOnlySession) {
                assertEquals("test", readOnlySession.getOrThrow(User.class, testUserId).getHandle());
            }
        });
    }

    @Test
    public void execute_withSession_doesUpdateDb() {
        final Id<User> testUserId = _transactor.save(new User("test", ZonedDateTime.now(PT)));
        _transactor.execute(new WithVoidSession() {
            @Override
            public void run(TypeSafeSessionWrapper session) {
                session.getOrThrow(User.class, testUserId).setHandle("not test");
            }
        });
        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(TypeSafeSessionWrapper readOnlySession) {
                assertEquals("not test", readOnlySession.getOrThrow(User.class, testUserId).getHandle());
            }
        });
    }

    @Test
    public void execute_exceptionThrown_withAnySession_rollsBackTransaction_propagatesException() {
        final Id<User> testUserId = _transactor.save(new User("test", ZonedDateTime.now(PT)));
        try {
            _transactor.execute(new WithVoidSession() {
                @Override
                public void run(TypeSafeSessionWrapper session) {
                    session.getOrThrow(User.class, testUserId).setHandle("not test");
                    throw new IllegalArgumentException("oh no you didnt");
                }
            });
            fail("expected inner exception to be propagated");
        } catch (IllegalArgumentException e) {
            assertEquals("oh no you didnt", e.getMessage());
        }
        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(TypeSafeSessionWrapper readOnlySession) {
                assertEquals("test", readOnlySession.getOrThrow(User.class, testUserId).getHandle());
            }
        });
    }

}