package com.gytmy.sound;

import java.util.List;
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
    public static final List<String> DEFAULT_UP = List.of("UP");
    public static final List<String> DEFAULT_DOWN = List.of("DOWN");
    public static final List<String> DEFAULT_LEFT = List.of("LEFT");
    public static final List<String> DEFAULT_RIGHT = List.of("RIGHT");

    private String firstName;
    private String lastName;
    private int studentNumber;
    private String userName;
    private boolean upToDate = true;
    private List<String> up = DEFAULT_UP;
    private List<String> down = DEFAULT_DOWN;
    private List<String> left = DEFAULT_LEFT;
    private List<String> right = DEFAULT_RIGHT;

    public User(User user) {
        this(user.getFirstName(), user.getLastName(), user.getStudentNumber(), user.getUserName(),
                user.getUp(), user.getDown(), user.getLeft(), user.getRight(), user.getUpToDate());
    }

    public User() {
        this(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_STUDENT_NUMBER, DEFAULT_USER_NAME, DEFAULT_UP,
                DEFAULT_DOWN, DEFAULT_LEFT, DEFAULT_RIGHT, true);
    }

    public User(String firstName, String lastName, int studentNumber, String userName, List<String> up,
            List<String> down, List<String> left, List<String> right) {
        this(firstName, lastName, studentNumber, userName, up, down, left, right, true);
    }

    public User(String fisrtName, String lastName, int studentNumber, String userName) {
        this(fisrtName, lastName, studentNumber, userName, DEFAULT_UP, DEFAULT_DOWN, DEFAULT_LEFT, DEFAULT_RIGHT);
    }

    public User(String firstName, String lastName, int studentNumber, String userName, List<String> up,
            List<String> down, List<String> left, List<String> right, boolean upToDate) {
        handleInvalidArguments(firstName, lastName, studentNumber, userName, up, down, left, right);

        this.firstName = firstName.toUpperCase().replace(" ", "_");
        this.lastName = lastName.toUpperCase().replace(" ", "_");
        this.studentNumber = studentNumber;
        this.userName = userName.replace(" ", "_");
        this.upToDate = upToDate;
    }

    private void handleInvalidArguments(String firstName, String lastName, int studentNumber, String userName,
            List<String> up, List<String> down, List<String> left, List<String> right) {
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

        if (isAltCmdsValid(up)) {
            throw new IllegalArgumentException("Invalid up commands");
        }

        if (isAltCmdsValid(down)) {
            throw new IllegalArgumentException("Invalid down commands");
        }

        if (isAltCmdsValid(left)) {
            throw new IllegalArgumentException("Invalid left commands");
        }

        if (isAltCmdsValid(right)) {
            throw new IllegalArgumentException("Invalid right commands");
        }
    }

    private boolean isNameInvalid(String name) {
        return name == null || name.isEmpty() || name.isBlank();
    }

    private boolean isAltCmdsValid(List<String> altCmds) {
        for (String altCmd : altCmds) {
            if (altCmd == null || !altCmd.matches("^[A-Z]*$")) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function checks if all users in a list are up-to-date and returns a
     * boolean value.
     * 
     * @param users A list of User objects.
     * @return If all users are up to date, it will return true;
     *         otherwise, it will return false.
     */
    public static boolean areUpToDate(List<User> users) {
        for (User user : users) {
            if (!user.getUpToDate()) {
                return false;
            }
        }
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.replace(" ", "_");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.replace(" ", "_");
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
        this.userName = userName.replace(" ", "_");
    }

    public List<String> getUp() {
        return up;
    }

    public void addAltUp(String altUp) {
        this.up.add(altUp);
    }

    public List<String> getDown() {
        return down;
    }

    public void addAltDown(String altDown) {
        this.down.add(altDown);
    }

    public List<String> getLeft() {
        return left;
    }

    public void addAltLeft(String altLeft) {
        this.left.add(altLeft);
    }

    public List<String> getRight() {
        return right;
    }

    public void addAltRight(String altRight) {
        this.right.add(altRight);
    }

    public boolean getUpToDate() {
        return upToDate;
    }

    public void setUpToDate(boolean upToDate) {
        this.upToDate = upToDate;
    }

    /**
     * A User could be associated with a `.yaml` file.
     *
     * Also, when building a User from a file, an empty User is built, then
     * each of its attributes is set with every available setters.
     *
     * And when building the `.yaml` file from the User, every attributes is
     * passed to the file using every available getters.
     *
     * So, the `.yaml` file contains every public attributes of the User and
     * nothing more. Moreover, only the public attributes should have
     * getters and setters beginning with `get` and `set`.
     */
    public String yamlConfigPath() {
        return audioFilesPath() + "config.yaml";
    }

    public String audioFilesPath() {
        return AUDIO_ROOT_DIRECTORY + getFirstName() + "/";
    }

    public String audioPath() {
        return audioFilesPath() + "audio/";
    }

    public String modelPath() {
        return audioFilesPath() + "model/";
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
                && user.getUserName().equals(this.getUserName())
                && user.getUp().equals(this.getUp())
                && user.getDown().equals(this.getDown())
                && user.getLeft().equals(this.getLeft())
                && user.getRight().equals(this.getRight());
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
