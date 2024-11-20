package com.andreferreira.tinybank.service;

import com.andreferreira.tinybank.domain.dao.UserDAO;
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
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}