package com.andreferreira.tinybank.api.request;

import com.andreferreira.tinybank.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserBody {
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String fullname;

    public UserBody() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public User toUser() {
        return new User(this.email, this.fullname);
    }
}
