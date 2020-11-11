package com.example.demo.models;


import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

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
    private Blob image;
    private long age;


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

        Date d1 = Calendar.getInstance().getTime();
        Date d2 = birthdate;

        this.age = ((d1.getTime()-d2.getTime())/(1000*60*60*24*365));


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

    public void setAge() {

        LocalDate today = LocalDate.now();
        String str = birthdate.toString();
        //System.out.println(str);
        String[] stra = str.split("-");

        LocalDate birthday = LocalDate.of(Integer.parseInt(stra[0]), Integer.parseInt(stra[1]), Integer.parseInt(stra[2]));
        //System.out.println(birthday);

        Period p = Period.between(birthday, today);

        //Now access the values as below
        //System.out.println(p.getDays());
        //System.out.println(p.getMonths());
        //System.out.println(p.getYears());


       this.age=p.getYears();
    }

    public long getAge() {
        return age;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInterestOne() {
        return interestOne;
    }

    public void setInterestOne(String interestOne) {
        this.interestOne = interestOne;
    }

    public String getInterestTwo() {
        return interestTwo;
    }

    public void setInterestTwo(String interestTwo) {
        this.interestTwo = interestTwo;
    }

    public String getInterestThree() {
        return interestThree;
    }

    public void setInterestThree(String interestThree) {
        this.interestThree = interestThree;
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
