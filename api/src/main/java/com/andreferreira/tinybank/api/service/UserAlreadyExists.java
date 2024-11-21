package com.andreferreira.tinybank.api.service;

import jakarta.validation.constraints.NotNull;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(@NotNull String email) {
        super("User with email " + email + " already exists");
    }
}
