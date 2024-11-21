package com.andreferreira.tinybank.api.request;

import jakarta.validation.constraints.Email;

public class TransferBody extends TransactionBody{
    @Email
    private String destination;

    public TransferBody() {
        super();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
