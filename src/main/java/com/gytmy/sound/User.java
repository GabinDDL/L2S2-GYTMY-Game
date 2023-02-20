package com.gytmy.sound;

import java.util.Objects;

public class User {

    private static final String PATH = "src/resources/audioFiles/";

    protected String firstName;
    protected String lastName;
    protected int numEtu;

    public User(String firstName, String lastName, int numEtu) {
        handleInvalidArguments(firstName, lastName, numEtu);

        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.numEtu = numEtu;
    }

    public User(String firstName, String lastName) {
        this(firstName, lastName, 2210000);
    }

    public User(String firstName) {
        this(firstName, "Default");
    }

    public User() {
        this("No-name");
    }

    private void handleInvalidArguments(String firstname, String lastname, int numEtu) {
        if (nameIsInvalid(firstname)) {
            throw new IllegalArgumentException("Invalid firstname");
        }

        if (nameIsInvalid(lastname)) {
            throw new IllegalArgumentException("Invalid lastname");
        }

        if (numEtu < 0) {
            throw new IllegalArgumentException("Invalid numEtu");
        }
    }

    private boolean nameIsInvalid(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            return true;
        }
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public int getNumEtu() {
        return numEtu;
    }

    public void setNumEtu(int numEtu) {
        this.numEtu = numEtu;
    }

    public String userYamlConfig() {
        return userAudioFilePath() + "config.yaml";
    }

    public String userAudioFilePath() {
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
                && user.getNumEtu() == this.getNumEtu();
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, numEtu);
    }

    @Override
    public String toString() {
        return "[" + numEtu + "] " + firstName + " " + lastName;
    }
}
