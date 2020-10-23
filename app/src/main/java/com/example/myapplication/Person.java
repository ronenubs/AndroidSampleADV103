package com.example.myapplication;

public class Person {
    private String firstname, lastname;
    private int personID;

    public Person(int personID, String lastname, String firstname) {
        this.personID = personID;
        this.firstname = firstname;
        this.lastname = lastname;
    }



    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }
}
