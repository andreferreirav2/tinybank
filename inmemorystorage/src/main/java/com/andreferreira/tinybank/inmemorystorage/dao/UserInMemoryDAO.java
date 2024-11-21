package com.andreferreira.tinybank.inmemorystorage.dao;

import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.dao.UserDAO;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserInMemoryDAO implements UserDAO {
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    @Override
    public boolean exists(@NotNull String email) {
        return users.containsKey(email);
    }

    @Override
    public Optional<User> find(@NotNull String email) {
        return Optional.ofNullable(users.get(email));
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public void save(@NotNull User user) {
        users.put(user.getEmail(), user);
    }
}
