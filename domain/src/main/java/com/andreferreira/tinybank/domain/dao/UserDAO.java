package com.andreferreira.tinybank.domain.dao;

import com.andreferreira.tinybank.domain.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Provides operations on collections of Users.
 * Behavior should be the same regardless of implementation.
 */
public interface UserDAO {
    /**
     * Checks if a user with the given email exists
     * */
    boolean exists(String email);

    /**
     * Returns the user with the corresponding email, if exists
     * */
    Optional<User> find(String email);

    /**
     * Returns all users
     * */
    Collection<User> findAll();

    /**
     * Creates or updates a user, using the email as unique key
     * */
    void save(User user);
}
