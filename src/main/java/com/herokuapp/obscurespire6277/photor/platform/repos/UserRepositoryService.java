package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.entities.User;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.Id;
import com.herokuapp.obscurespire6277.photor.platform.models.UserView;

import java.util.Optional;

public interface UserRepositoryService {
    Optional<UserView> getUserWithId(Id<User> id);

    void saveTokenToUser(Id<User> id, String longToken);
}
