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
				user.getUserName() == User.DEFAULT_USER_NAME &&
				user.getUp().equals(User.UP) &&
				user.getDown().equals(User.DOWN) &&
				user.getLeft().equals(User.LEFT) &&
				user.getRight().equals(User.RIGHT);
	}

	@Test
	public void testConstructorInvalidFirstName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(null, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME,
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid first name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("", User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME,
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid first name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User("      ", User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME,
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid first name");
	}

	@Test
	public void testConstructorInvalidLastName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, null, User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME,
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid last name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, "", User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME,
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid last name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, "      ", User.DEFAULT_STUDENT_NUMBER, User.DEFAULT_USER_NAME, 
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid last name");
	}

	@Test
	public void testConstructorInvalidStudentNumber() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, -1, User.DEFAULT_USER_NAME,
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid student number");
	}

	@Test
	public void testConstructorInvalidUserName() {

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, null,
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid user name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, "",
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid user name");

		TestingUtils.assertArgumentExceptionMessage(
				() -> new User(User.DEFAULT_FIRST_NAME, User.DEFAULT_LAST_NAME, User.DEFAULT_STUDENT_NUMBER, "      ",
				User.UP, User.DOWN, User.LEFT, User.RIGHT),
				"Invalid user name");
	}
}
