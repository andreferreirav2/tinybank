package com.andreferreira.tinybank.inmemorystorage.dao;

import com.andreferreira.tinybank.domain.Transaction;
import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.dao.TransactionDAO;
import com.andreferreira.tinybank.domain.exception.InactiveUser;
import com.andreferreira.tinybank.domain.exception.InsufficientFunds;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;

public class TransactionInMemoryDAO implements TransactionDAO {
    private final List<Transaction> transactions = new LinkedList<>();

    @Override
    public List<Transaction> findAll() {
        synchronized (transactions) {
            return Collections.unmodifiableList(transactions);
        }
    }

    @Override
    public List<Transaction> findAllByUser(User user) {
        synchronized (transactions) {
            return transactions.stream()
                    .filter(transaction -> transaction.concernsUser(user))
                    .sorted()
                    .toList();
        }
    }

    @Override
    public void save(Transaction transaction) throws InactiveUser, InsufficientFunds {
        synchronized (transactions) {
            validateTransaction(transaction);

            transactions.add(transaction);
            Collections.sort(transactions);
        }
    }

    @Override
    public long balance(User user) {
        LongAdder longAdder = new LongAdder();
        this.findAllByUser(user)
                .forEach(transaction -> longAdder.add(transaction.getAmountChange(user)));
        return longAdder.longValue();
    }

    private void validateTransaction(Transaction transaction) {
        validateTransactionForUser(transaction, transaction.getSourceUser());
        validateTransactionForUser(transaction, transaction.getDestinationUser());
    }

    private void validateTransactionForUser(Transaction transaction, Optional<User> userOptional) {
        if (userOptional.isEmpty()) {
            return;
        }

        User user = userOptional.get();
        if (!user.isActive()) {
            throw new InactiveUser();
        }

        int amountChange = transaction.getAmountChange(user);
        long balance = this.balance(user);
        if (balance + amountChange < 0) {
            throw new InsufficientFunds();
        }
    }
}
