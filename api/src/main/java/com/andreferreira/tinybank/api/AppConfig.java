package com.andreferreira.tinybank.api;

import com.andreferreira.tinybank.domain.dao.TransactionDAO;
import com.andreferreira.tinybank.domain.dao.UserDAO;
import com.andreferreira.tinybank.inmemorystorage.dao.TransactionInMemoryDAO;
import com.andreferreira.tinybank.inmemorystorage.dao.UserInMemoryDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class AppConfig {

    @Bean
    public UserDAO userDAO() {
        return new UserInMemoryDAO();
    }

    @Bean
    public TransactionDAO transactionDAO() {
        return new TransactionInMemoryDAO();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}