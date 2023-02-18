package com.gytmy.sound;

import java.util.Objects;

public class User {

    private static final String PATH = "src/resources/audioFiles/";

    protected String firstname;
    protected String lastname;
    protected int numEtu;

    public User(String firstname, String lastname, int numEtu) {
        handleInvalidArguments(firstname, lastname, numEtu);

        this.firstname = firstname.toUpperCase();
        this.lastname = lastname.toUpperCase();
        this.numEtu = numEtu;
    }

    public User(String firstname, String lastname) {
        this(firstname, lastname, 2210000);
    }

    public User(String firstname) {
        this(firstname, "Default");
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getNumEtu() {
        return numEtu;
    }

    public void setNumEtu(int numEtu) {
        this.numEtu = numEtu;
    }

    public String userYamlConfig() {
        return PATH + getFirstname() + "/config.yaml";
    }

    public String userAudioFilePath() {
        return PATH + getFirstname() + "/";
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
        return user.getFirstname().equals(this.getFirstname())
                && user.getLastname().equals(this.getLastname())
                && user.getNumEtu() == this.getNumEtu();
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, numEtu);
    }

    @Override
    public String toString() {
        return "[" + numEtu + "] " + firstname + " " + lastname;
    }
}
