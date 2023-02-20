package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestUser {

	@Test
	public void testConstructor() {

		User user = new User("DEFAULT", "DEFAULT", 22100000);
		assertTrue(user.getFirstName().equals("DEFAULT") &&
				user.getLastName().equals("DEFAULT") &&
				user.getStudentNumber() == 2210000);
	}

	@Test
	public void testConstructorInvalidFirstName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(null, "", 0),
				"Invalid first name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("", "", 0),
				"Invalid first name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("      ", "", 0),
				"Invalid first name");
	}

	@Test
	public void testConstructorInvalidLastName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("FirstName", null, 0),
				"Invalid last name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("FirstName", "", 0),
				"Invalid last name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("FirstName", "      ", 0),
				"Invalid last name");
	}

	@Test
	public void testConstructorInvalidStudentNumber() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("FirstName", "LastName", -1),
				"Invalid student number");
	}
}
