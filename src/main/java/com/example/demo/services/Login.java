package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;


public class Login {
    private String username;
    private String password;

    public String verifyLogin(){
        UserRepository users = new UserRepository();
        if(users.verifyLogin(username,password)){
            return "redirect:/loggedin";
        }
        else return "redirect:/!";
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
