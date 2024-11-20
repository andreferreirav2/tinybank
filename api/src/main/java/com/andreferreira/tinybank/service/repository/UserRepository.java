package com.andreferreira.tinybank.service.repository;

import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.dao.UserDAO;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRepository {

    private final UserDAO userDAO;

    @Autowired
    public UserRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User get(String email) throws UserNotFound {
        Optional<User> userOptional = userDAO.find(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFound(email);
        }
        return userOptional.get();
    }

    public List<User> getAll() {
        return userDAO.findAll().stream().toList();
    }

    public List<User> getPage(long start, long count) {
        return userDAO.findAll().stream().skip(start).limit(count).toList();
    }

    public void create(@NotNull User user) throws UserAlreadyExists {
        if (userDAO.exists(user.getEmail())) {
            throw new UserAlreadyExists(user.getEmail());
        }

        userDAO.save(user);
    }

    public void update(@NotNull User user) throws UserNotFound {
        if (!userDAO.exists(user.getEmail())) {
            throw new UserNotFound(user.getEmail());
        }

        userDAO.save(user);
    }
}
