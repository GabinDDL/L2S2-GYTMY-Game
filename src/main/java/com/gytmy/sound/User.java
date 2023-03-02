package com.gytmy.sound;

import java.util.Objects;

/**
 * User Class model a confirmed user of the application.
 */
public class User {

    private static final String AUDIO_ROOT_DIRECTORY = "src/resources/audioFiles/";

    public static final String DEFAULT_FIRST_NAME = "FIRST_NAME";
    public static final String DEFAULT_LAST_NAME = "LAST_NAME";
    public static final int DEFAULT_STUDENT_NUMBER = 22100000;
    public static final String DEFAULT_USER_NAME = "USER_NAME";

    private String firstName;
    private String lastName;
    private int studentNumber;
    private String userName;

    public User(String firstName, String lastName, int studentNumber, String userName) {
        handleInvalidArguments(firstName, lastName, studentNumber, userName);

        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.studentNumber = studentNumber;
        this.userName = userName;
    }

    public User() {
        this(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_STUDENT_NUMBER, DEFAULT_USER_NAME);
    }

    private void handleInvalidArguments(String firstName, String lastName, int studentNumber, String userName) {
        if (isNameInvalid(firstName)) {
            throw new IllegalArgumentException("Invalid first name");
        }

        if (isNameInvalid(lastName)) {
            throw new IllegalArgumentException("Invalid last name");
        }

        if (studentNumber < 0) {
            throw new IllegalArgumentException("Invalid student number");
        }

        if (isNameInvalid(userName)) {
            throw new IllegalArgumentException("Invalid user name");
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /***********************************
     * To avoid putting these PATHS in *
     * the `yaml` file, we do not want *
     * to put `get` in the method name *
     ***********************************/

    public String yamlConfigPath() {
        return audioFilesPath() + "config.yaml";
    }

    public String audioFilesPath() {
        return AUDIO_ROOT_DIRECTORY + getFirstName() + "/";
    }

    /**********************************/

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
                && user.getStudentNumber() == this.getStudentNumber()
                && user.getUserName().equals(this.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, studentNumber, userName);
    }

    @Override
    public String toString() {
        return "[" + studentNumber + "] " + firstName + " " + lastName + " {" + userName + "}";
    }
}
