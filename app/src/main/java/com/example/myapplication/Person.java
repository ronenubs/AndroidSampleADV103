package com.example.myapplication;

public class Person {
    private String firstname, lastname;

    public Person(String lastname, String firstname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
