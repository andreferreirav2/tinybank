package com.andreferreira.tinybank.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TransactionTest {
    private final LocalDateTime now = LocalDateTime.now();
    private final LocalDateTime after = now.plusHours(1);

    private final User USER_1 = new User("user1@mail.com", "User One");
    private final User USER_2 = new User("user2@mail.com", "User Two");
    private final User USER_3 = new User("user3@mail.com", "User Three");

    private final int AMOUNT = 100;

    @Test
    void testWithdraw() {
        Transaction withdraw = Transaction.Withdraw(now, AMOUNT, USER_1);

        Assertions.assertEquals(AMOUNT, withdraw.getAmount());
        Assertions.assertTrue(withdraw.concernsUser(USER_1));
        Assertions.assertEquals(-AMOUNT, withdraw.getAmountChange(USER_1));
        Assertions.assertFalse(withdraw.concernsUser(USER_2));
        Assertions.assertEquals(0, withdraw.getAmountChange(USER_2));

        Assertions.assertTrue(withdraw.getSourceUser().isPresent());
        Assertions.assertEquals(USER_1, withdraw.getSourceUser().get());

        Assertions.assertFalse(withdraw.getDestinationUser().isPresent());
    }

    @Test
    void testDeposit() {
        Transaction deposit = Transaction.Deposit(now, AMOUNT, USER_1);

        Assertions.assertEquals(AMOUNT, deposit.getAmount());
        Assertions.assertTrue(deposit.concernsUser(USER_1));
        Assertions.assertEquals(AMOUNT, deposit.getAmountChange(USER_1));
        Assertions.assertFalse(deposit.concernsUser(USER_2));
        Assertions.assertEquals(0, deposit.getAmountChange(USER_2));

        Assertions.assertFalse(deposit.getSourceUser().isPresent());

        Assertions.assertTrue(deposit.getDestinationUser().isPresent());
        Assertions.assertEquals(USER_1, deposit.getDestinationUser().get());
    }

    @Test
    void testTransfer() {
        Transaction transfer = Transaction.Transfer(now, AMOUNT, USER_1, USER_2);

        Assertions.assertEquals(AMOUNT, transfer.getAmount());
        Assertions.assertTrue(transfer.concernsUser(USER_1));
        Assertions.assertEquals(-AMOUNT, transfer.getAmountChange(USER_1));
        Assertions.assertTrue(transfer.concernsUser(USER_2));
        Assertions.assertEquals(AMOUNT, transfer.getAmountChange(USER_2));

        Assertions.assertTrue(transfer.getSourceUser().isPresent());
        Assertions.assertEquals(USER_1, transfer.getSourceUser().get());

        Assertions.assertTrue(transfer.getDestinationUser().isPresent());
        Assertions.assertEquals(USER_2, transfer.getDestinationUser().get());

        Assertions.assertFalse(transfer.concernsUser(USER_3));
    }

    @Test
    void transactionsAreComparedByTimestamp() {
        Transaction transferNow = Transaction.Deposit(now, AMOUNT, USER_1);
        Transaction transferAfter = Transaction.Deposit(after, AMOUNT, USER_1);

        Assertions.assertEquals(0, transferNow.compareTo(transferNow));
        Assertions.assertTrue(transferNow.compareTo(transferAfter) < 0);
        Assertions.assertTrue(transferAfter.compareTo(transferNow) > 0);
    }
}