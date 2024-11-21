package com.andreferreira.tinybank.api.request;

import jakarta.validation.constraints.Min;

public class TransactionBody {
    @Min(1)
    private int amount;

    public TransactionBody() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
