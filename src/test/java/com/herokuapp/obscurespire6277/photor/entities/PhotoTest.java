package com.herokuapp.obscurespire6277.photor.entities;

import com.google.common.collect.Iterables;
import com.herokuapp.obscurespire6277.photor.PersistentTestBase;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.TypeSafeSessionWrapper;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.WithSession;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.WithVoidReadOnlySession;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import org.junit.Test;

import java.io.IOException;
import java.time.ZonedDateTime;

import static com.herokuapp.obscurespire6277.photor.util.time.TimeZones.PT;
import static org.junit.Assert.assertEquals;

public class PhotoTest extends PersistentTestBase {

    @Test
    public void testPersistence() throws IOException  {
        final ZonedDateTime now = ZonedDateTime.now(PT);
        final Id<User> userWithPhotos = _transactor.execute(new WithSession<Id<User>>() {
            @Override
            public Id<User> run(TypeSafeSessionWrapper session) {
                User user = new User("will", now, new FacebookUserId("FB ID"));
                Id<User> userId = session.save(user);

                Photo photo = new Photo(now, "wills_face.jpg");
                user.addPhoto(photo);
                session.save(photo);

                return userId;
            }
        });

        _transactor.execute(new WithVoidReadOnlySession() {
            @Override
            public void run(TypeSafeSessionWrapper readOnlySession) {
                User user = readOnlySession.getOrThrow(User.class, userWithPhotos);

                Photo photo = Iterables.getOnlyElement(user.getPhotos());
                assertEquals(now, photo.getCreatedAt());
            }
        });
    }

}
