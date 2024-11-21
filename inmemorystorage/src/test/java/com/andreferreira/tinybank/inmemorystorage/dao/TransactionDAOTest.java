package com.andreferreira.tinybank.inmemorystorage.dao;

import com.andreferreira.tinybank.domain.Transaction;
import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.dao.TransactionDAO;
import com.andreferreira.tinybank.domain.exception.InactiveUser;
import com.andreferreira.tinybank.domain.exception.InsufficientFunds;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDAOTest {
    private final LocalDateTime before = LocalDateTime.of(1000, 1, 1, 0, 0);
    private final LocalDateTime now = LocalDateTime.of(2000, 1, 1, 0, 0);
    private final LocalDateTime after = LocalDateTime.of(3000, 1, 1, 0, 0);

    private final User USER_1 = new User("user1@mail.com", "User One");
    private final User USER_2 = new User("user2@mail.com", "User Two");

    private final Transaction TRANSACTION_1 = Transaction.Deposit(before, 100, USER_1);
    private final Transaction TRANSACTION_2 = Transaction.Withdraw(now, 10, USER_1);
    private final Transaction TRANSACTION_3 = Transaction.Transfer(after, 50, USER_1, USER_2);

    TransactionDAO transactionDAO;

    @BeforeEach
    void setUp() {
        transactionDAO = new TransactionInMemoryDAO();
    }

    @AfterEach
    void tearDown() {
        USER_1.activate();
        USER_2.activate();
    }

    @Test
    public final void findAllIsEmptyByDefault() {
        assertTrue(transactionDAO.findAll().isEmpty());
    }

    @Test
    public final void transactionExistsAfterBeingAdded() {
        transactionDAO.save(TRANSACTION_1);

        List<Transaction> transactionList = transactionDAO.findAll();
        assertEquals(1, transactionList.size());
        assertTrue(transactionList.contains(TRANSACTION_1));
    }

    @Test
    public final void transactionsAreSortedByTimestamp() {
        transactionDAO.save(TRANSACTION_1);
        transactionDAO.save(TRANSACTION_3);
        transactionDAO.save(TRANSACTION_2);

        List<Transaction> transactionList = transactionDAO.findAll();
        assertEquals(3, transactionList.size());
        assertEquals(List.of(TRANSACTION_1, TRANSACTION_2, TRANSACTION_3), transactionList);
    }

    @Test
    public final void transactionsAreFilteredByUser() {
        transactionDAO.save(TRANSACTION_1);
        transactionDAO.save(TRANSACTION_3);
        transactionDAO.save(TRANSACTION_2);

        List<Transaction> user1TransactionList = transactionDAO.findAllByUser(USER_1);
        assertEquals(3, user1TransactionList.size());
        assertEquals(List.of(TRANSACTION_1, TRANSACTION_2, TRANSACTION_3), user1TransactionList);

        List<Transaction> user2TransactionList = transactionDAO.findAllByUser(USER_2);
        assertEquals(1, user2TransactionList.size());
        assertEquals(List.of(TRANSACTION_3), user2TransactionList);
    }

    @Test
    public final void balanceStartsAtZero() {
        assertEquals(0, transactionDAO.balance(USER_1));
        assertEquals(0, transactionDAO.balance(USER_2));
    }

    @Test
    public final void balanceIncreasesWithDeposits() {
        transactionDAO.save(TRANSACTION_1);

        assertEquals(100, transactionDAO.balance(USER_1));
    }

    @Test
    public final void balanceReflectsMultipleTransactions() {
        transactionDAO.save(TRANSACTION_1);
        transactionDAO.save(TRANSACTION_2);
        transactionDAO.save(TRANSACTION_3);

        assertEquals(40, transactionDAO.balance(USER_1));
        assertEquals(50, transactionDAO.balance(USER_2));
    }

    @Test
    public final void balanceCannotBeNegative() {
        assertThrows(InsufficientFunds.class, () -> transactionDAO.save(TRANSACTION_3));
    }

    @Test
    public final void cannotPerformTransactionsOnInactiveUsers() {
        USER_1.deactivate();
        USER_2.deactivate();

        assertThrows(InactiveUser.class, () -> transactionDAO.save(TRANSACTION_1));
        assertThrows(InactiveUser.class, () -> transactionDAO.save(TRANSACTION_2));
        assertThrows(InactiveUser.class, () -> transactionDAO.save(TRANSACTION_3));
    }
}