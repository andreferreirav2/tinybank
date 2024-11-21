package com.andreferreira.tinybank.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    private final String EMAIL_1 = "john.doe@example.com";
    private final String FULLNAME_1 = "John Doe";

    @Test
    public void cannotCreateUserWithNullValues() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {new User(EMAIL_1, null);});
        Assertions.assertThrows(IllegalArgumentException.class, () -> {new User(null, FULLNAME_1);});
        Assertions.assertThrows(IllegalArgumentException.class, () -> {new User(null, null);});
    }

    @Test
    public void canCreateUser() {
        User user = new User(EMAIL_1, FULLNAME_1);

        Assertions.assertEquals(EMAIL_1, user.getEmail());
        Assertions.assertEquals(FULLNAME_1, user.getFullname());
    }

    @Test
    public void canDisableAndEnableUser() {
        User user = new User(EMAIL_1, FULLNAME_1);
        Assertions.assertTrue(user.isActive());

        user.deactivate();
        Assertions.assertFalse(user.isActive());

        user.activate();
        Assertions.assertTrue(user.isActive());
    }
}
