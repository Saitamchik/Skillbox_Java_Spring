package com.example.basicsSpring;

public class Contacts {

    private String fullName;
    private String phoneNumber;
    private String email;

    public Contacts(String fullName, String phoneNumber, String email) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return fullName + ";" + phoneNumber + ";" + email;
    }
}
