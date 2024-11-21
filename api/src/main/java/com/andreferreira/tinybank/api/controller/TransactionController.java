package com.andreferreira.tinybank.api.controller;

import com.andreferreira.tinybank.api.request.TransactionBody;
import com.andreferreira.tinybank.api.request.TransferBody;
import com.andreferreira.tinybank.api.service.TransactionService;
import com.andreferreira.tinybank.api.service.UserNotFound;
import com.andreferreira.tinybank.api.service.UserService;
import com.andreferreira.tinybank.domain.Transaction;
import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.exception.InactiveUser;
import com.andreferreira.tinybank.domain.exception.InsufficientFunds;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class TransactionController {

    private final UserService userService;
    private final TransactionService transactionService;

    public TransactionController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{email}/balance")
    public long userBalance(@PathVariable @Email String email) {
        try {
            User user = userService.get(email);
            return transactionService.balance(user);
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{email}/transactions")
    public List<Transaction> userTransctions(@PathVariable @Email String email,
                                             @RequestParam(required = false, defaultValue = "1") @Min(1) long page,
                                             @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(100) long count) {
        try {
            User user = userService.get(email);
            return transactionService.findByUserPaged(user, (page - 1) * count, count);
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path="/{email}/transactions/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Transaction deposit(@PathVariable @Email String email, @RequestBody @Valid TransactionBody transactionBody) {
        try {
            User user = userService.get(email);
            return transactionService.deposit(transactionBody.getAmount(), user);
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InactiveUser e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PostMapping(path="/{email}/transactions/withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Transaction withdraw(@PathVariable @Email String email, @RequestBody @Valid TransactionBody transactionBody) {
        try {
            User user = userService.get(email);
            return transactionService.withdraw(transactionBody.getAmount(), user);
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InsufficientFunds | InactiveUser e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PostMapping(path="/{email}/transactions/transfer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Transaction withdraw(@PathVariable @Email String email, @RequestBody @Valid TransferBody transferPayload) {
        try {
            User sourceUser = userService.get(email);
            User destinationUser = userService.get(transferPayload.getDestination());
            return transactionService.transfer(transferPayload.getAmount(), sourceUser, destinationUser);
        } catch (UserNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InsufficientFunds | InactiveUser e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
