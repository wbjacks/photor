package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.*;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserDoesNotExistException;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@PetiteBean("userRepositoryService")
public class UserRepositoryServiceImpl implements UserRepositoryService {
    private static final Logger _logger = Logger.getLogger(UserRepositoryService.class);
    private static final String PACIFIC_TIME_ZONE_ID = "PST";

    private final Transactor _transactor;

    @PetiteInject
    public UserRepositoryServiceImpl(Transactor transactor) {
        _transactor = transactor;
    }

    @Override
    public Id<User> saveUserLoginAndUpdateToken(String facebookUserId, String facebookToken) throws UserDoesNotExistException {
        Optional<Id<User>> maybeUserId = Optional.ofNullable(_transactor.execute(new WithSession<Id<User>>() {
            @Override
            public Id<User> run(TypeSafeSessionWrapper session) {
                Optional<User> maybeUser = session.getByUniqueFieldValue(User.class, "facebookUserId", facebookUserId);
                if (maybeUser.isPresent()) {
                    maybeUser.get().setFacebookLongToken(facebookToken);
                    session.update(maybeUser.get());
                    return maybeUser.get().getId();
                } else {
                    return null;
                }
            }
        }));
        return maybeUserId.orElseThrow(() -> new UserDoesNotExistException("No user found when updating login information, user likely has not signed up."));
    }

    @Override
    public UserView createUserFromFacebookData(String facebookUserId, String facebookToken) {
        return _transactor.execute(new WithSession<UserView>() {
            @Override
            public UserView run(TypeSafeSessionWrapper session) {
                User user = new User("TESTHANDLE", ZonedDateTime.now(ZoneId.of(PACIFIC_TIME_ZONE_ID, ZoneId.SHORT_IDS)));
                user.setFacebookLongToken(facebookToken);
                user.setFacebookUserId(facebookUserId);
                session.save(user);
                return UserView.fromHibernateEntity(user);
            }
        });
    }

    @Override
    public UserView getUser(Id<User> userId) throws UserDoesNotExistException {
        try {
            return _transactor.execute(new WithReadOnlySession<UserView>() {
                @Override
                public UserView run(TypeSafeSessionWrapper readOnlySession) {
                    return UserView.fromHibernateEntity(readOnlySession.getOrThrow(User.class, userId));
                }
            });
        } catch (HibernateException e) {
            throw new UserDoesNotExistException("FATAL ERROR: User for supplied ID does not exist");
        }
    }
}