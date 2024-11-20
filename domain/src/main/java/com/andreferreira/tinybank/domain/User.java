package com.andreferreira.tinybank.domain;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

public class User  {
    private final String email;
    private final String fullname;
    private UserActivationStatus status;

    public User(@NotNull @Email String email, @NotNull String fullname) {
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
