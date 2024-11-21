package com.andreferreira.tinybank.api.service;

import com.andreferreira.tinybank.domain.Transaction;
import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.dao.TransactionDAO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionDAO transactionDAO;

    @Autowired
    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public List<Transaction> findByUserPaged(@NotNull User user, long start, long count) {
        return transactionDAO.findAllByUser(user).stream().skip(start).limit(count).toList();
    }

    public long balance(@NotNull User user) {
        return transactionDAO.balance(user);
    }

    public Transaction deposit(@Min(1) int amount, @NotNull User user) {
        Transaction deposit = Transaction.Deposit(LocalDateTime.now(), amount, user);
        transactionDAO.save(deposit);
        return deposit;
    }

    public Transaction withdraw(@Min(1) int amount, @NotNull User user) {
        Transaction withdraw = Transaction.Withdraw(LocalDateTime.now(), amount, user);
        transactionDAO.save(withdraw);
        return withdraw;
    }

    public Transaction transfer(@Min(1) int amount, @NotNull User source, @NotNull User destination) {
        Transaction transfer = Transaction.Transfer(LocalDateTime.now(), amount, source, destination);
        transactionDAO.save(transfer);
        return transfer;
    }
}
