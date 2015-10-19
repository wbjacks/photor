package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.entities.UserEntity;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.User;

import java.util.Optional;

public interface UserRepositoryService {
    Optional<User> getUserWithId(Id<UserEntity> id);

    void saveTokenToUser(Id<UserEntity> id, String longToken);
}
