package com.andreferreira.tinybank.domain.exception;

public class InsufficientFunds extends RuntimeException {
    public InsufficientFunds() {
        super("Insufficient Funds");
    }
}
