package com.andreferreira.tinybank.domain;


/**
 * User record
 * Email is used as unique key
 * */
public class User  {
    private final String email;
    private final String fullname;
    private UserActivationStatus status;

    public User(String email, String fullname) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (fullname == null || fullname.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.email = email;
        this.fullname = fullname;
        this.status = UserActivationStatus.ACTIVE;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullname() {
        return this.fullname;
    }

    public boolean isActive() {
        return this.status == UserActivationStatus.ACTIVE;
    }

    public void activate() {
        this.status = UserActivationStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = UserActivationStatus.INACTIVE;
    }

    private enum UserActivationStatus {
        ACTIVE,
        INACTIVE
    }
}
