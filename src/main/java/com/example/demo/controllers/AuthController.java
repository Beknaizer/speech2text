package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repositories.UserRepo;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class AuthController {

    ExecutorService threadExecutor = Executors.newCachedThreadPool();


    @Autowired
    private UserRepo userRepo;


    @GetMapping("/login")
    public String loginPage(){
        return "auth";
    }

//    @GetMapping("/registration")
//    public String registrationPage(){
//        return "registration";
//    }

    @PostMapping("/registration")
    public String registration(@RequestParam(name = "firstname") String firstname,
                        @RequestParam(name = "lastname") String lastname,
                        @RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "email") String email,
                        @RequestParam(name = "phone") String phone,
                               Model model) throws ExecutionException, InterruptedException {

        List<User> userList = userRepo.getUsers();
        Future<Boolean> futureCall1 = threadExecutor.submit(new EmailChecker(email));
        Future<Boolean> futureCall2 = threadExecutor.submit(new UsernamePasswordChecker(username,password));
        boolean result1 =  futureCall1.get();
        boolean result2 = futureCall2.get();
        boolean isExist = false;
        for (User user: userList){
            if(user.getEmail().equals(email)) isExist = true;
        }

        if(isExist){
            model.addAttribute("error","user already exist!");
            return "auth";
        }
        if(!result1){
            model.addAttribute("error","Invalid format of email!");
            return "auth";
        }
        if(!result2){
            model.addAttribute("error","Invalid format of username or password!");
            return "auth";
        }

        User currentUser = new User(username,password,firstname,lastname,email,phone, Role.USER);
        userRepo.addUser(currentUser);
        model.addAttribute("user",currentUser);
        return "mainPage";

    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        Model model) throws ExecutionException, InterruptedException {
        List<User> userList = userRepo.getUsers();

        for(User user : userList){
            if(user.getPassword().equals(password) && user.getUsername().equals(username)){
                model.addAttribute("user",user);
                return "mainPage";
            }
        }

        return "auth";
    }
}
