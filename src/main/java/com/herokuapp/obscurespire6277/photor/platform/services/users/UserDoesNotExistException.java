package com.herokuapp.obscurespire6277.photor.platform.services.users;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}