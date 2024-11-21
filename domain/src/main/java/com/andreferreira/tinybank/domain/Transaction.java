package com.andreferreira.tinybank.domain;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Transaction record
 * If only has source is a withdrawal
 * If only has destination is a deposit
 * If it has both source and destination is a transfer
 * */
public class Transaction implements Comparable<Transaction> {
    final LocalDateTime timestamp;
    final int amount;
    final User source;
    final User destination;

    public static Transaction Withdraw(LocalDateTime timestamp, int amount, User source) {
        if (source == null) {
            throw new IllegalArgumentException("Withdraw source cannot be null");
        }
        return new Transaction(timestamp, amount, source, null);
    }

    public static Transaction Deposit(LocalDateTime timestamp, int amount, User destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Deposit destination cannot be null");
        }
        return new Transaction(timestamp, amount, null, destination);
    }

    public static Transaction Transfer(LocalDateTime timestamp, int amount, User source, User destination) {
        if (source == null) {
            throw new IllegalArgumentException("Withdraw user cannot be null");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Deposit destination cannot be null");
        }
        return new Transaction(timestamp, amount, source, destination);
    }

    private Transaction(LocalDateTime timestamp, int amount, User source, User destination) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Transaction timestamp is null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than 0");
        }
        if (source == null && destination == null) {
            throw new IllegalArgumentException("Transaction requires either source or destination users to not be null");
        }



        this.timestamp = timestamp;
        this.amount = amount;
        this.source = source;
        this.destination = destination;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getAmount() {
        return amount;
    }

    public Optional<User> getSourceUser() {
        return Optional.ofNullable(source);
    }

    public Optional<User> getDestinationUser() {
        return Optional.ofNullable(destination);
    }

    public boolean concernsUser(User user) {
        if (source != null && source.equals(user)) {
            return true;
        }
        if (destination != null && destination.equals(user)) {
            return true;
        }

        return false;
    }

    public int getAmountChange(User user) {
        if (source != null && source.equals(user)) {
            return -amount;
        }
        if (destination != null && destination.equals(user)) {
            return amount;
        }

        return 0;
    }

    @Override
    public int compareTo(Transaction o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}
