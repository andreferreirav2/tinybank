package com.andreferreira.tinybank.domain.dao;

import com.andreferreira.tinybank.domain.Transaction;
import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.exception.InactiveUser;
import com.andreferreira.tinybank.domain.exception.InsufficientFunds;

import java.util.List;

/**
 * Provides operations on collections of Transactions.
 * Behavior should be the same regardless of implementation.
 */
public interface TransactionDAO {
    /**
     * Gets all transactions from all users, sorted by timestamp
     * */
    List<Transaction> findAll();

    /**
     * Gets all transactions of user, sorted by timestamp
     * */
    List<Transaction> findAllByUser(User user);

    /**
     * Adds a new transaction
     * */
    void save(Transaction transaction) throws InactiveUser, InsufficientFunds;

    /**
     * Returns the balance of a user reflected by all transactions that concern the it
     * */
    long balance(User user);
}
