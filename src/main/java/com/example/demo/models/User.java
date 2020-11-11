package com.example.demo.models;


import java.util.Date;

public class User {
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String username;
    private String password;
    private String gender;
    private String genderPreference;
    private String phonenumber;
    private String comment;
    private String interestOne;
    private String interestTwo;
    private String interestThree;


    public User(String firstName, String lastName, Date birthdate, String username, String password, String gender, String genderPreference, String phonenumber, String comment, String interestOne, String interestTwo, String interestThree) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.genderPreference = genderPreference;
        this.phonenumber = phonenumber;
        this.comment = comment;
        this.interestOne = interestOne;
        this.interestTwo = interestTwo;
        this.interestThree = interestThree;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                ", username='" + username +
                ", gender='" + gender +
                ", preference='" + genderPreference;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(String genderPreference) {
        this.genderPreference = genderPreference;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
