package com.andreferreira.tinybank.domain.dao;

import com.andreferreira.tinybank.domain.User;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface UserDAO {
    boolean exists(@NotNull String email);
    Optional<User> find(@NotNull String email);
    Collection<User> findAll();
    BigDecimal count();
    void save(@NotNull User user);
}
