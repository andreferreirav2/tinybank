package com.andreferreira.tinybank.service.controller;

import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.service.dto.UserDTO;
import com.andreferreira.tinybank.service.repository.UserAlreadyExists;
import com.andreferreira.tinybank.service.repository.UserNotFound;
import com.andreferreira.tinybank.service.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<User> getUsers(@RequestParam(required = false, defaultValue = "1") @Min(1) long page,
                               @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(100) long count) {
        return userRepository.getPage((page - 1) * count, count);
    }

    @GetMapping("/{email}")
    public User getUser(@PathVariable @Email String email) {
        return userRepository.get(email);
    }

    @PostMapping(path="",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody UserDTO userDTO) {
        try {
            User newUser = userDTO.toUser();
            userRepository.create(newUser);
            return newUser;
        } catch (UserAlreadyExists e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/activate/{email}")
    public String activateUser(@PathVariable @Email String email) {
        try {
            User user = userRepository.get(email);
            if (user.isActive()) {
                return "User is already active";
            }

            user.activate();
            userRepository.update(user);
            return "User activated";
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/deactivate/{email}")
    public String deactivateUser(@PathVariable @Email String email) {
        try {
            User user = userRepository.get(email);
            if (!user.isActive()) {
                return "User is already inactive";
            }

            user.deactivate();
            userRepository.update(user);
            return "User deactivated";
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
