package com.playtomic.pactdemo.users.domain;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("The user does not exist");
    }
}
