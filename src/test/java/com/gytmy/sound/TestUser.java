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
				user.getStudentNumber() == User.DEFAULT_STUDENT_NUMBER &&
				user.getUserName() == User.DEFAULT_USER_NAME;
	}

	@Test
	public void testConstructorInvalidFirstName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(null, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME),
				"Invalid first name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("", User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME),
				"Invalid first name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("      ", User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME),
				"Invalid first name");
	}

	@Test
	public void testConstructorInvalidLastName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, null, User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME),
				"Invalid last name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, "", User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME),
				"Invalid last name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, "      ", User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME),
				"Invalid last name");
	}

	@Test
	public void testConstructorInvalidStudentNumber() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, -1, User.DEFAULT_USER_NAME),
				"Invalid student number");
	}

	@Test
	public void testConstructorInvalidUserName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, null),
				"Invalid user name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, ""),
				"Invalid user name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, "      "),
				"Invalid user name");
	}
}
