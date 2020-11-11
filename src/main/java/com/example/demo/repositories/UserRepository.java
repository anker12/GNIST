package com.example.demo.repositories;


import com.example.demo.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // ESTABLISH CONNECTION METHOD
    private Connection establishConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/datingsys","root","admin");
        return conn;
    }

    // FIND SINGLE USER
    public User findSingleUser(String username){
        User temp = null;
        try {
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users where username = ?");
            ps.setString(1,username);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                temp = new User(
                        //firstname
                        rs.getString(2),
                        //lastname
                        rs.getString(3),
                        //birthdate
                        rs.getDate(6),
                        //username
                        rs.getString(4),
                        //password
                        rs.getString(5),
                        //gender
                        rs.getString(7),
                        //gender preference
                        rs.getString(8),
                        //phonenumber
                        rs.getString(9),
                        //comment
                        rs.getString(10),
                        //interest 1
                        rs.getString(11),
                        //interest 2
                        rs.getString(12),
                        //interest 3
                        rs.getString(13)
                );

            }

        }
        catch (SQLException e){
            System.out.println("hmm");
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            return null;
        }

        return temp;
    }

    // FIND ALL USERS METHOD
    public List<User> findAllUsers(){
        List<User> allUsers = new ArrayList<User>();

        try{
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                User temp = new User(
                        //firstname
                        rs.getString(2),
                        //lastname
                        rs.getString(3),
                        //birthdate
                        rs.getDate(6),
                        //username
                        rs.getString(4),
                        //password
                        rs.getString(5),
                        //gender
                        rs.getString(7),
                        //gender preference
                        rs.getString(8),
                        //phonenumber
                        rs.getString(9),
                        //comment
                        rs.getString(10),
                        //interest 1
                        rs.getString(11),
                        //interest 2
                        rs.getString(12),
                        //interest 3
                        rs.getString(13)
                );
                allUsers.add(temp);
            }

        } catch (SQLException e){
            System.out.println("hmm");
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            return null;
        }
        return allUsers;
    }


    // VERIFY LOGIN METHOD
    public Boolean verifyLogin(String username, String password){
        boolean booltmp = false;

        try{
            PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM users");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String tempUNstr = rs.getString(4);
                String tempPWstr = rs.getString(5);
                if(tempUNstr.equals(username)){
                    if(tempPWstr.equals(password)){
                        booltmp = true;
                        break;
                    }
                }
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return booltmp;
    }

    // CREATE USER METHOD
    public String createUserInDatabase(String username, String password, String passwordre, String firstName, String lastName, Date birthdate, String gender, String genderPreference, String phonenumber, String comment, String interestOne, String interestTwo, String interestThree){
        //checks if all fields has been filled
        if(!username.equals("") && !password.equals("") && !firstName.equals("") && !lastName.equals("") &&birthdate!=null&&!gender.equals("Vælg her")&&!genderPreference.equals("Vælg her")){
            //checks if the password in both fields match
            if(password.equals(passwordre)){
                try{
                    PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO users (firstName, lastName, username, password, birthdate, gender, preference, phonenumber, comment, interestOne, interestTwo, interestThree)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?);");
                    ps.setString(1,firstName);
                    ps.setString(2,lastName);
                    ps.setString(3,username);
                    ps.setString(4,password);
                    ps.setDate(5,birthdate);
                    ps.setString(6,gender);
                    ps.setString(7,genderPreference);
                    ps.setString(8,phonenumber);
                    ps.setString(9,comment);
                    ps.setString(10,interestOne);
                    ps.setString(11,interestTwo);
                    ps.setString(12,interestThree);

                    ps.executeUpdate();

                    // everything went fine and user was created
                    return "redirect:/oprettet";

                } catch (SQLException e){
                    System.out.println(e.getMessage());
                    return null;
                }
                // password doesnt match
            } else {
                System.out.println("password ikke ens");
                return "redirect:/obPWNotMatching";
            }
            // not all fields has been filled
        } else {
            System.out.println("Venligst udfyld alle felter");
            return "redirect:/obFillAll";
        }



    }



}
