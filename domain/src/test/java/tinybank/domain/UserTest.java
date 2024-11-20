package tinybank.domain;

import com.andreferreira.tinybank.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testUserCreation() {
        final String email = "email@domain.com";
        final String fullname = "Full name";

        User user = new User(email, fullname);

        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(fullname, user.getFullname());
    }

    @Test
    public void testUserDisableEnable() {
        final String email = "email@domain.com";
        final String fullname = "Full name";

        User user = new User(email, fullname);
        Assertions.assertTrue(user.isActive());

        user.deactivate();
        Assertions.assertFalse(user.isActive());

        user.activate();
        Assertions.assertTrue(user.isActive());
    }
}
