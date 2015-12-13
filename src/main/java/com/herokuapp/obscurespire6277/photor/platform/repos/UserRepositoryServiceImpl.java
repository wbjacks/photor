package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.*;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import com.herokuapp.obscurespire6277.photor.platform.services.users.UserDoesNotExistException;
import com.herokuapp.obscurespire6277.photor.util.time.TimeZones;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import java.time.ZonedDateTime;
import java.util.Optional;

@PetiteBean("userRepositoryService")
public class UserRepositoryServiceImpl implements UserRepositoryService {
    private static final Logger _logger = Logger.getLogger(UserRepositoryService.class);

    private final Transactor _transactor;

    @PetiteInject
    public UserRepositoryServiceImpl(Transactor transactor) {
        _transactor = transactor;
    }

    @Override
    public Id<User> saveUserLoginAndUpdateToken(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) throws UserDoesNotExistException {
        Optional<Id<User>> maybeUserId = _transactor.execute(new WithSession<Optional<Id<User>>>() {
            @Override
            public Optional<Id<User>> run(TypeSafeSessionWrapper session) {
                Optional<User> maybeUser = session.getByUniqueFieldValue(User.class, "facebookUserId", facebookUserId);
                if (maybeUser.isPresent()) {
                    maybeUser.get().setFacebookLongToken(facebookLongToken);
                    session.update(maybeUser.get());
                    return Optional.of(maybeUser.get().getId());
                } else {
                    return Optional.empty();
                }
            }
        });
        return maybeUserId.orElseThrow(() -> new UserDoesNotExistException("No user found when updating login information, user likely has not signed up."));
    }

    @Override
    public UserView createUserFromFacebookData(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken, String handle) {
        return _transactor.execute(new WithSession<UserView>() {
            @Override
            public UserView run(TypeSafeSessionWrapper session) {
                User user = new User(handle, ZonedDateTime.now(TimeZones.PT), facebookUserId);
                user.setFacebookLongToken(facebookLongToken);
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

    @Override
    public boolean isUserSignedUp(FacebookUserId facebookUserId, FacebookLongToken facebookLongToken) {
        return _transactor.execute(new WithReadOnlySession<Boolean>() {
            @Override
            public Boolean run(TypeSafeSessionWrapper readOnlySession) {
                return readOnlySession.getByUniqueFieldValue(User.class, "facebookUserId", facebookUserId.getId()).isPresent();
            }
        });
    }

    @Override
    public boolean isHandleAvailable(String handle) {
        return _transactor.execute(new WithReadOnlySession<Boolean>() {
            @Override
            public Boolean run(TypeSafeSessionWrapper readOnlySession) {
                return !readOnlySession.getByUniqueFieldValue(User.class, "handle", handle).isPresent();
            }
        });
    }
}