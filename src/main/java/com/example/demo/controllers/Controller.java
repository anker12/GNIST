package com.example.demo.controllers;


import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.Login;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    private boolean loggedIn = false;
    UserRepository users = new UserRepository();


    @GetMapping("/")
    public String index(){
        loggedIn=false;
        List<User> allUsers = users.findAllUsers();
        System.out.println(allUsers);
        return "index";
    }

    @GetMapping("/opret")
    public String create(){
        return "opret-bruger";
    }

    @GetMapping("/oprettet")
    public String userCreated(){
        return "bruger-oprettet";
    }

    @GetMapping("/loggedin")
    public String loggedin(){
        if(!loggedIn){
            return "redirect:/";
        }
        else
            return "loggedin";
    }


    // "errors" in forms

    @GetMapping("/!")
    public String indexWrongPW(){
        return "indexWrongPW";
    }

    //TODO fix
    @GetMapping("/obFillAll")
    public String createFillAll(){
        return "opret-bruger";
    }
    @GetMapping("/obPWNotMatching")
    public String createPWNotMatching(){
        return "opret-bruger";
    }

    // "functions"

    //login
    @GetMapping("/login")
    public String login(WebRequest dataFromForm){
        String u = dataFromForm.getParameter("username");
        String p = dataFromForm.getParameter("password");
        //System.out.println(users.verifyLogin(u,p));
        Login ln = new Login(u,p);
        if(ln.verifyLogin().equals("redirect:/loggedin")){
            loggedIn=true;
        }
        //System.out.println(u);
        //System.out.println(ln.verifyLogin());
        return ln.verifyLogin();
    }

    //create user
    @GetMapping("/createUser")
    public String createUser(WebRequest dataFromForm) throws ParseException {
        String username = dataFromForm.getParameter("username");
        String password = dataFromForm.getParameter("password");
        String passwordre = dataFromForm.getParameter("passwordre");
        String firstName = dataFromForm.getParameter("firstName");
        String lastName = dataFromForm.getParameter("lastName");
        String birthdateStr = dataFromForm.getParameter("birthdate");
        // birthdate conversion and to be sure program doesnt crash if leaved empty in form
        Date birthdate;
        if(!birthdateStr.equals("")){birthdate = Date.valueOf(birthdateStr);}
        else{birthdate = null;}

        String gender = dataFromForm.getParameter("gender");
        String genderPreference = dataFromForm.getParameter("genderPreference");

        assert password != null;

        //String tmpRtnStr = users.createUserInDatabase(username,password,passwordre,firstName,lastName,birthdate,gender,genderPreference);
        //System.out.println(tmpRtnStr);
        return users.createUserInDatabase(username,password,passwordre,firstName,lastName,birthdate,gender,genderPreference);
    }







}
