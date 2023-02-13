package com.gytmy.sound;

public class User {

    protected String firstname;
    protected String lastname;
    protected int numEtu;

    public User(String firstname, String lastname, int numEtu) {
        handleInvalidArguments(firstname, lastname, numEtu);

        this.firstname = firstname;
        this.lastname = lastname;
        this.numEtu = numEtu;
    }

    public User(String firstname, String lastname) {
        this(firstname, lastname, 2210000);
    }

    public User(String firstname) {
        this(firstname, "Default");
    }

    public User() {
        this("NoName");
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
        if (name == null || name.isEmpty()) {
            return true;
        }
        return isEntirelySpace(name);
    }

    private boolean isEntirelySpace(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isSpaceChar(name.charAt(i))) {
                return false;
            }
        }
        return true;
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

    @Override
    public String toString() {
        return "[" + numEtu + "]" + firstname + " " + lastname;
    }
}
