package com.andreferreira.tinybank.service.dto;

import com.andreferreira.tinybank.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String email;
    private String fullname;

    public User toUser() {
        return new User(this.email, this.fullname);
    }
}
