package com.andreferreira.tinybank.inmemorystorage.dao;

import com.andreferreira.tinybank.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserInMemoryDAOTest {

    final String TEST_EMAIL_1 = "test1@email.com";
    final String TEST_EMAIL_2 = "test2@email.com";

    final String FULL_NAME_1 = "John Doe";
    final String FULL_NAME_2 = "Jane Doe";

    final User USER_1 = new User(TEST_EMAIL_1, FULL_NAME_1);
    final User USER_2_SAME_EMAIL_DIFFERENT_NAME = new User(TEST_EMAIL_1, FULL_NAME_2);

    UserInMemoryDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserInMemoryDAO();
    }

    @Test
    void noUserExistByDefault() {
        assertFalse(userDAO.exists(TEST_EMAIL_1));
        assertFalse(userDAO.exists(TEST_EMAIL_2));
    }

    @Test
    void userExistsAfterBeingAdded() {
        userDAO.save(USER_1);

        assertTrue(userDAO.exists(TEST_EMAIL_1));
        assertFalse(userDAO.exists(TEST_EMAIL_2));
    }

    @Test
    void findAllIsEmptyByDefault() {
        assertTrue(userDAO.findAll().isEmpty());
        Assertions.assertEquals(BigDecimal.ZERO, userDAO.count());
    }

    @Test
    void findAllContainsUserAfterBeingAdded() {
        userDAO.save(USER_1);

        Collection<User> allUsers = userDAO.findAll();
        assertEquals(1, allUsers.size());
        Assertions.assertEquals(BigDecimal.ONE, userDAO.count());
        assertTrue(allUsers.contains(USER_1));
    }

    @Test
    void savingSameEmailUpdatesRecord() {
        userDAO.save(USER_1);
        assertTrue(userDAO.findAll().contains(USER_1));

        userDAO.save(USER_2_SAME_EMAIL_DIFFERENT_NAME);

        assertFalse(userDAO.findAll().contains(USER_1));
        assertTrue(userDAO.findAll().contains(USER_2_SAME_EMAIL_DIFFERENT_NAME));

    }
}