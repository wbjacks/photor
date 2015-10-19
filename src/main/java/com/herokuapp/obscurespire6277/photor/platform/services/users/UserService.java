package com.herokuapp.obscurespire6277.photor.platform.services.users;

import java.util.Optional;

public interface UserService {
    Optional<String> logInUser(String userId, String facebookShortToken);
}
