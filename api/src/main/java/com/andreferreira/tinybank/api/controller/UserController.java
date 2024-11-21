package com.andreferreira.tinybank.api.controller;

import com.andreferreira.tinybank.api.request.UserBody;
import com.andreferreira.tinybank.api.service.UserAlreadyExists;
import com.andreferreira.tinybank.api.service.UserNotFound;
import com.andreferreira.tinybank.api.service.UserService;
import com.andreferreira.tinybank.domain.User;
import jakarta.validation.Valid;
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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getUsers(@RequestParam(required = false, defaultValue = "1") @Min(1) long page,
                               @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(100) long count) {
        return userService.findAllPaged((page - 1) * count, count);
    }

    @GetMapping("/{email}")
    public User getUser(@PathVariable @Email String email) {
        return userService.get(email);
    }

    @PostMapping(path="",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody @Valid UserBody userBody) {
        try {
            User newUser = userBody.toUser();
            userService.create(newUser);
            return newUser;
        } catch (UserAlreadyExists e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{email}/activate")
    public String activateUser(@PathVariable @Email String email) {
        try {
            User user = userService.get(email);
            if (userService.activate(user)) {
                return "User activated";
            } else {
                return "User is already active";
            }
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{email}/deactivate")
    public String deactivateUser(@PathVariable @Email String email) {
        try {
            User user = userService.get(email);
            if (userService.deactivate(user)) {
                return "User deactivated";
            } else {
                return "User is already inactive";
            }
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
