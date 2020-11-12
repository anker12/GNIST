package com.example.demo.repositories;


import com.example.demo.models.User;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
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
                temp.setImage(rs.getBlob(14));
                temp.setFavs(rs.getString(15));

                if(rs.getString(16)!=null){
                    temp.setAdmin(rs.getString(16));
                }

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
                temp.setAge();
                if(rs.getString(16)==null){
                    allUsers.add(temp);
                }

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

    // IS ADMIN


    // CREATE USER METHOD
    public String createUserInDatabase(String username, String password, String passwordre, String firstName, String lastName, Date birthdate, String gender, String genderPreference, String phonenumber, String comment, String interestOne, String interestTwo, String interestThree){
    //checks if the password in both fields match
        if(password.equals(passwordre)){
            try{
                PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO users (firstName, lastName, username, password, birthdate, gender, preference, phonenumber, comment, interestOne, interestTwo, interestThree)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
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


    }

    //UPDATE USER IN DATABASE
    public String updateUserInDatabase(String username,String comment, String phonenumber, String password, String passwordre){
        if(password.equals(passwordre)){
            try {
                PreparedStatement ps = establishConnection().prepareStatement("UPDATE `datingsys`.`users` SET `password` = ?, `phonenumber` = ?, `comment` = ? WHERE (`username` = ?);");

                ps.setString(4,username);
                ps.setString(1,password);
                ps.setString(2,phonenumber);
                ps.setString(3,comment);

                ps.executeUpdate();


            }catch (SQLException e){
                System.out.println(e.getMessage());
                return null;
            }


        } else {
            System.out.println("password ikke ens");
            return "redirect:/profil";
        }


        return "redirect:/profil";
    }

    //UPLOAD PHOTO METHOD
    public String uploadPhotoToDatabase(Blob photo,String username){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE `users` SET `photo` = ? WHERE (`username` = ?);");

            ps.setBlob(1,photo);
            ps.setString(2,username);

            ps.executeUpdate();

            getImageFromUsername(username);
            return "redirect:/profil";


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

    //GET IMAGE
    public void getImageFromUsername(String username){
        try {
            PreparedStatement ps2 = establishConnection().prepareStatement("select photo from users where (username=?)");
            ps2.setString(1, username);

            ResultSet rs = ps2.executeQuery();


            File image = new File("src/main/resources/static/images/"+username+".jpeg");
            FileOutputStream output = new FileOutputStream(image);

            if(rs.next()){
                InputStream input = rs.getBinaryStream("photo");

                byte[] buffer = new byte[1024];
                while(input.read(buffer) >0){
                    output.write(buffer);
                }

            }





        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // LIST POTENTIAL MATCHES
    public List<User> potentialMatches(String genderPreference, String gender, String username) {
        ArrayList<User> all = (ArrayList<User>) findAllUsers();
        ArrayList<User> pot = new ArrayList<>();


        for(int i = 0; i<all.size();i++){
            if(!all.get(i).getUsername().equals(username)) {
                all.get(i).setAge();
                if (all.get(i).getGender().equals(genderPreference) && all.get(i).getGenderPreference().equals(gender)) {
                    pot.add(all.get(i));
                }

                if (all.get(i).getGenderPreference().equals("both") && all.get(i).getGender().equals(genderPreference)) {
                    pot.add(all.get(i));
                }

                if (all.get(i).getGenderPreference().equals(gender) && genderPreference.equals("both")) {
                    pot.add(all.get(i));
                }
                if (all.get(i).getGenderPreference().equals("both") && genderPreference.equals("both")) {
                    pot.add(all.get(i));
                }


            }



        }


        return pot;
    }

    //SET FAVOURITES
    public void setFavs(String usernameLoggedin, String usernameToAdd){
        User tmpu= findSingleUser(usernameLoggedin);
        tmpu.setFavs(tmpu.getFavs()+","+usernameToAdd);

        try {
            PreparedStatement ps = establishConnection().prepareStatement("UPDATE `users` SET `favourites` = ? WHERE (`username` = ?);");

            ps.setString(1,tmpu.getFavs());
            ps.setString(2,usernameLoggedin);

            ps.executeUpdate();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }



    //GET FAVOURITES
    public ArrayList<User> getFavs(String username){
        ArrayList<User> favs = new ArrayList<>();

        User tmpu= findSingleUser(username);
        String tmpstr = tmpu.getFavs();
        if(tmpstr!=null) {
            String[] stra = tmpstr.split(",");

            for (int i = 1; i < stra.length; i++) {
                favs.add(findSingleUser(stra[i]));
                favs.get(i-1).setAge();
            }
        }

        return favs;
    }

    //DELETE USER
    public void deleteUser(String username){
        try {
            PreparedStatement ps = establishConnection().prepareStatement("DELETE FROM `datingsys`.`users` WHERE (`username` = ?);");

            ps.setString(1,username);

            ps.executeUpdate();

            File f = new File("src/main/resources/static/images/"+username+".jpeg");
            f.delete();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



}
