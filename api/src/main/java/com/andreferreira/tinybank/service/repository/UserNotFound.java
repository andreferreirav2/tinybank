package com.andreferreira.tinybank.service.repository;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String email) {
        super("User with email " + email + " not found");
    }
}
