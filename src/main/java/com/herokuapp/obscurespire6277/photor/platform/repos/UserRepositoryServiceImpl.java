package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.entities.UserEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.*;
import com.herokuapp.obscurespire6277.photor.platform.models.User;
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
    public Optional<User> getUserWithId(Id<UserEntity> id) {
         return _transactor.execute(new WithReadOnlySession<Optional<User>>() {
            @Override
            public Optional<User> run(TypeSafeSessionWrapper readOnlySession) {
                Optional<UserEntity> userEntity = readOnlySession.get(UserEntity.class, id);
                if (userEntity.isPresent()) {
                    return Optional.of(User.fromHibernateEntity(userEntity.get()));
                }
                else {
                    return Optional.empty();
                }
            }
        });
    }

    @Override
    public void saveTokenToUser(Id<UserEntity> id, String longToken) {
        _transactor.execute(new WithVoidSession() {
            @Override
            public void run(TypeSafeSessionWrapper session) {
                session.getOrThrow(UserEntity.class, id).setFacebookLongToken(longToken);
            }
        });
    }
}