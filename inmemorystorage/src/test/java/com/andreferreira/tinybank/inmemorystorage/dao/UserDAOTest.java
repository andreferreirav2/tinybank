package com.andreferreira.tinybank.inmemorystorage.dao;

import com.andreferreira.tinybank.domain.User;
import com.andreferreira.tinybank.domain.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    final String TEST_EMAIL_1 = "test1@email.com";
    final String TEST_EMAIL_2 = "test2@email.com";

    final String FULL_NAME_1 = "John Doe";
    final String FULL_NAME_2 = "Jane Doe";

    final User USER_1 = new User(TEST_EMAIL_1, FULL_NAME_1);
    final User USER_2_SAME_EMAIL_DIFFERENT_NAME = new User(TEST_EMAIL_1, FULL_NAME_2);

    UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserInMemoryDAO();
    }

    @Test
    public final void noUserExistByDefault() {
        assertFalse(userDAO.exists(TEST_EMAIL_1));
        assertFalse(userDAO.exists(TEST_EMAIL_2));
    }

    @Test
    public final void userExistsAfterBeingAdded() {
        userDAO.save(USER_1);

        assertTrue(userDAO.exists(TEST_EMAIL_1));
        assertFalse(userDAO.exists(TEST_EMAIL_2));
    }

    @Test
    public final void findAllIsEmptyByDefault() {
        assertTrue(userDAO.findAll().isEmpty());
        Assertions.assertTrue(userDAO.findAll().isEmpty());
    }

    @Test
    public final void findAllContainsUserAfterBeingAdded() {
        userDAO.save(USER_1);

        Collection<User> allUsers = userDAO.findAll();
        assertEquals(1, allUsers.size());
        assertTrue(allUsers.contains(USER_1));
    }

    @Test
    public final void savingSameEmailUpdatesRecord() {
        userDAO.save(USER_1);
        assertTrue(userDAO.findAll().contains(USER_1));

        userDAO.save(USER_2_SAME_EMAIL_DIFFERENT_NAME);

        assertFalse(userDAO.findAll().contains(USER_1));
        assertTrue(userDAO.findAll().contains(USER_2_SAME_EMAIL_DIFFERENT_NAME));

    }
}