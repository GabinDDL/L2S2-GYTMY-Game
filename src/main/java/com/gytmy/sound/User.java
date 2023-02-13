package com.gytmy.sound;

public class User {

    protected String firstname;
    protected String lastname;
    protected int numEtu;

    public User(String firstname, String lastname, int numEtu) {
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
