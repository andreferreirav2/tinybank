package com.andreferreira.tinybank.api.service;

import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.dao.UserDAO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User get(@Email String email) {
        Optional<User> userOptional = userDAO.find(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFound(email);
        }
        return userOptional.get();
    }

    public List<User> findAllPaged(long start, long count) {
        return userDAO.findAll().stream().skip(start).limit(count).toList();
    }

    public void create(@NotNull User user) {
        if (userDAO.exists(user.getEmail())) {
            throw new UserAlreadyExists(user.getEmail());
        }

        userDAO.save(user);
    }

    public void update(@NotNull User user) {
        if (!userDAO.exists(user.getEmail())) {
            throw new UserNotFound(user.getEmail());
        }

        userDAO.save(user);
    }

    public boolean activate(@NotNull User user) {
        if (user.isActive()) {
            return false;
        }

        user.activate();
        this.update(user);
        return true;
    }

    public boolean deactivate(@NotNull User user) {
        if (!user.isActive()) {
            return false;
        }

        user.deactivate();
        this.update(user);
        return true;
    }
}
