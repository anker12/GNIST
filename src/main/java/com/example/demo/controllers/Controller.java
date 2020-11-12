package com.example.demo.controllers;


import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.Login;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Controller
public class Controller {

    private boolean loggedIn = false;
    UserRepository users = new UserRepository();
    User whosLoggedIn = null;


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
    public String loggedin(Model model){
        if(!loggedIn){
            return "redirect:/";
        }
        else {
            if(whosLoggedIn.getAdmin().equals("admin")){
                return "redirect:/adminAdgang";
            }else {

                //users.getImageFromUsername(whosLoggedIn.getUsername());

                System.out.println(whosLoggedIn.getUsername());
                System.out.println(whosLoggedIn.getImage());
                System.out.println(users.potentialMatches(whosLoggedIn.getGenderPreference(), whosLoggedIn.getGender(), whosLoggedIn.getUsername()));
                System.out.println("favoritter: " + users.getFavs(whosLoggedIn.getUsername()));


                model.addAttribute("imagePath", whosLoggedIn.getUsername() + ".jpeg");
                model.addAttribute("users", users.potentialMatches(whosLoggedIn.getGenderPreference(), whosLoggedIn.getGender(), whosLoggedIn.getUsername()));


                return "loggedin";
            }
        }
    }
    @GetMapping("/adminAdgang")
    public String adminAccess(Model model){
        if(loggedIn) {
            if (whosLoggedIn.getAdmin().equals("admin")) {
                model.addAttribute("allusers", users.findAllUsers());
                return "admin-adgang";
            } else {
                loggedIn = false;
                return "redirect:/";
            }
        }else {
            loggedIn = false;
            return "redirect:/";
        }

    }


    @GetMapping("/profil")
    public String profil(Model model){
        if(!loggedIn){
            return "redirect:/";
        }else{
            if(whosLoggedIn.getAdmin().equals("admin")){
                return "redirect:/adminAdgang";
            }else {

                model.addAttribute("imagePath", whosLoggedIn.getUsername() + ".jpeg");
                model.addAttribute("singleUser", users.findSingleUser(whosLoggedIn.getUsername()));


                return "profil";
            }
        }
    }

    @GetMapping("/favoritter")
    public String favourites(Model model){
        if(!loggedIn){
            return "redirect:/";
        }else {
            if(whosLoggedIn.getAdmin().equals("admin")){
                return "redirect:/adminAdgang";
            }else {

                model.addAttribute("imagePath", whosLoggedIn.getUsername() + ".jpeg");
                model.addAttribute("users", users.getFavs(whosLoggedIn.getUsername()));


                return "favoritter";
            }
        }
    }


    // "errors" in forms

    @GetMapping("/!")
    public String indexWrongPW(){
        return "indexWrongPW";
    }


    @GetMapping("/obPWNotMatching")
    public String createPWNotMatching(){
        return "redirect:/opret";
    }

    // "functions"

    // DELETE USER

    @GetMapping("/deluser")
    public String deleteUser(WebRequest dataFromForm){
        //System.out.println("SÅ JEG KAN SE DET: "+dataFromForm.getParameter("userN"));
        users.deleteUser(dataFromForm.getParameter("userN"));

        return "redirect:/adminAdgang";
    }


    //login
    @GetMapping("/login")
    public String login(WebRequest dataFromForm){
        String u = dataFromForm.getParameter("username");
        String p = dataFromForm.getParameter("password");
        //System.out.println(users.verifyLogin(u,p));
        Login ln = new Login(u,p);
        if(ln.verifyLogin().equals("redirect:/loggedin")){
            loggedIn=true;
            whosLoggedIn=users.findSingleUser(u);
            System.out.println("Bruger: " + whosLoggedIn + " er logget ind");
        }
        //System.out.println(u);
        //System.out.println(ln.verifyLogin());
        return ln.verifyLogin();
    }

    //create user
    @GetMapping("/createUser")
    public String createUser(WebRequest dataFromForm) throws ParseException, FileNotFoundException {
        String username = dataFromForm.getParameter("username");
        String password = dataFromForm.getParameter("password");
        String passwordre = dataFromForm.getParameter("passwordre");
        String firstName = dataFromForm.getParameter("firstName");
        String lastName = dataFromForm.getParameter("lastName");
        String birthdateStr = dataFromForm.getParameter("birthdate");
        // birthdate conversion and to be sure program doesnt crash if left empty in form
        Date birthdate;
        if(!birthdateStr.equals("")){birthdate = Date.valueOf(birthdateStr);}
        else{birthdate = null;}

        String gender = dataFromForm.getParameter("gender");
        String genderPreference = dataFromForm.getParameter("genderPreference");
        String phonenumber = dataFromForm.getParameter("phonenumber");
        String comment = dataFromForm.getParameter("comment");
        String interestOne = dataFromForm.getParameter("interest1");
        String interestTwo = dataFromForm.getParameter("interest2");
        String interestThree = dataFromForm.getParameter("interest3");



        assert password != null;

        //String tmpRtnStr = users.createUserInDatabase(username,password,passwordre,firstName,lastName,birthdate,gender,genderPreference);
        //System.out.println(tmpRtnStr);
        return users.createUserInDatabase(username,password,passwordre,firstName,lastName,birthdate,gender,genderPreference, phonenumber,comment,interestOne,interestTwo,interestThree);
    }

    //UPDATE INF
    @GetMapping("/opdaterInfo")
    public String updateInfo(WebRequest dataFromForm, Model model){
        String username = whosLoggedIn.getUsername();
        String comment = dataFromForm.getParameter("comment");
        String phonenumber = dataFromForm.getParameter("phonenumber");
        String password = dataFromForm.getParameter("password");
        String passwordre = dataFromForm.getParameter("passwordre");

        model.addAttribute("imagePath", whosLoggedIn.getUsername()+".jpeg");
        model.addAttribute("users",users.potentialMatches(whosLoggedIn.getGenderPreference(),whosLoggedIn.getGender(),whosLoggedIn.getUsername()));

        //users.updateUserInDatabase(username,comment,phonenumber,password,passwordre);


        return users.updateUserInDatabase(username,comment,phonenumber,password,passwordre);

    }


    // UPLOAD IMAGE
    @PostMapping("/uploadBillede")
    public String uploadPhoto(@RequestParam("img") MultipartFile img) throws IOException, SQLException {
        byte[] fileAsBytes = img.getBytes();
        Blob fileAsBlob = new SerialBlob(fileAsBytes);


        return users.uploadPhotoToDatabase(fileAsBlob,whosLoggedIn.getUsername());
    }

    // SAVE IMAGE TO SYSTEM
    @GetMapping("/save")
    public String savePhoto(){

        users.getImageFromUsername(whosLoggedIn.getUsername());


        return"redirect:/profil";
    }

    //ADD TO FAVOURITES
    @GetMapping("/addfav")
    public String addToFavs(WebRequest dataFromForm){
        System.out.println("SÅ JEG KAN SE DET: "+dataFromForm.getParameter("userN"));

        users.setFavs(whosLoggedIn.getUsername(),dataFromForm.getParameter("userN"));


        return "redirect:/loggedin";
    }








}
