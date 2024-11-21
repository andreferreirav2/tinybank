package com.andreferreira.tinybank.domain.exception;

public class InactiveUser extends RuntimeException {
    public InactiveUser() {
        super("User is inactive");
    }
}
