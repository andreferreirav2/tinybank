package com.andreferreira.tinybank.api.service;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String email) {
        super("User with email " + email + " not found");
    }
}
