package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.*;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.Optional;

@PetiteBean("userRepositoryService")
public class UserRepositoryServiceImpl implements UserRepositoryService {
    public Transactor _transactor;

    @PetiteInject
    public UserRepositoryServiceImpl(Transactor transactor) {
        _transactor = transactor;
    }

    @Override
    public Optional<UserView> getUserWithId(Id<User> id) {
         return _transactor.execute(new WithReadOnlySession<Optional<UserView>>() {
            @Override
            public Optional<UserView> run(TypeSafeSessionWrapper readOnlySession) {
                Optional<User> userEntity = readOnlySession.get(User.class, id);
                if (userEntity.isPresent()) {
                    return Optional.of(UserView.fromHibernateEntity(userEntity.get()));
                }
                else {
                    return Optional.empty();
                }
            }
        });
    }

    @Override
    public void saveTokenToUser(Id<User> id, String longToken) {
        _transactor.execute(new WithVoidSession() {
            @Override
            public void run(TypeSafeSessionWrapper session) {
                session.getOrThrow(User.class, id).setFacebookLongToken(longToken);
            }
        });
    }
}