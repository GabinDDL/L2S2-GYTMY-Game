package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestUser {

	@Test
	public void testConstructor() {

		User user = new User();
		assertTrue(assertAreDefaultAttributes(user));
	}

	private boolean assertAreDefaultAttributes(User user) {
		return user.getFirstName().equals(User.DEFAULT_FIRST_NAME) &&
				user.getLastName().equals(User.DEFAULT_LAST_NAME) &&
				user.getStudentNumber() == User.DEFAULT_STUDENT_NUMBER;
	}

	@Test
	public void testConstructorInvalidFirstName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(null, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER),
				"Invalid first name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("", User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER),
				"Invalid first name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("      ", User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER),
				"Invalid first name");
	}

	@Test
	public void testConstructorInvalidLastName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, null, User.DEFAULT_STUDENT_NUMBER),
				"Invalid last name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, "", User.DEFAULT_STUDENT_NUMBER),
				"Invalid last name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, "      ", User.DEFAULT_STUDENT_NUMBER),
				"Invalid last name");
	}

	@Test
	public void testConstructorInvalidStudentNumber() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, -1),
				"Invalid student number");
	}
}
