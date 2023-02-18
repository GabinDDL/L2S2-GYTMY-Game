package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestUser {

    @Test
    public void testConstructor() {

        User user = new User();
        assertTrue(user.getFirstname().equals("NO-NAME") &&
                user.getLastname().equals("DEFAULT") &&
                user.getNumEtu() == 2210000);
    }

    @Test
    public void testConstructorInvalidFirstname() {

        TestingUtils.assertArgumentExceptionMessage(
                () -> new User(null), "Invalid firstname");

        TestingUtils.assertArgumentExceptionMessage(
                () -> new User(""), "Invalid firstname");

        TestingUtils.assertArgumentExceptionMessage(
                () -> new User("      "), "Invalid firstname");
    }

    @Test
    public void testConstructorInvalidLastname() {

        TestingUtils.assertArgumentExceptionMessage(
                () -> new User("Firstname", null), "Invalid lastname");

        TestingUtils.assertArgumentExceptionMessage(
                () -> new User("Firstname", ""), "Invalid lastname");

        TestingUtils.assertArgumentExceptionMessage(
                () -> new User("Firstname", "      "), "Invalid lastname");
    }

    @Test
    public void testConstructorInvalidNumEtu() {

        TestingUtils.assertArgumentExceptionMessage(
                () -> new User("Firstname", "Lastname", -1), "Invalid numEtu");
    }
}
