package com.gytmy.sound;

import java.util.Objects;

/**
 * User Class model a confirmed user of the application.
 * A Path is attributed to each user and is the root path of its associated
 * audio folder.
 */
public class User {

    private static final String PATH = "src/resources/audioFiles/";

    public static final String DEFAULT_FIRST_NAME = "FIRST_NAME";
    public static final String DEFAULT_LAST_NAME = "LAST_NAME";
    public static final int DEFAULT_STUDENT_NUMBER = 22100000;

    private String firstName;
    private String lastName;
    private int studentNumber;

    public User(String firstName, String lastName, int studentNumber) {
        handleInvalidArguments(firstName, lastName, studentNumber);

        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.studentNumber = studentNumber;
    }

    public User() {
        this(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_STUDENT_NUMBER);
    }

    private void handleInvalidArguments(String firstName, String lastName, int studentNumber) {
        if (isNameInvalid(firstName)) {
            throw new IllegalArgumentException("Invalid first name");
        }

        if (isNameInvalid(lastName)) {
            throw new IllegalArgumentException("Invalid last name");
        }

        if (studentNumber < 0) {
            throw new IllegalArgumentException("Invalid student number");
        }
    }

    private boolean isNameInvalid(String name) {
        return name == null || name.isEmpty() || name.isBlank();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String yamlConfigPath() {
        return audioFilesPath() + "config.yaml";
    }

    public String audioFilesPath() {
        return PATH + getFirstName() + "/";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User) obj;
        return user.getFirstName().equals(this.getFirstName())
                && user.getLastName().equals(this.getLastName())
                && user.getStudentNumber() == this.getStudentNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, studentNumber);
    }

    @Override
    public String toString() {
        return "[" + studentNumber + "] " + firstName + " " + lastName;
    }
}
