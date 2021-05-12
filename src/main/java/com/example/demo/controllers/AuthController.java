package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class AuthController {

    @Autowired
    private UserRepo userRepo;


    @GetMapping("/")
    public String loginPage(){
        return "index";
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

        User currentUser = new User(username,password,firstname,lastname,email,phone, Role.USER);
        userRepo.addUser(currentUser);
        model.addAttribute("user",currentUser);
        return "mainPage";

    }

//    @GetMapping("/firebaseConfig")
//    @ResponseBody
//    public JSONObject getConfig(){
//        JSONObject jsonObject = null;
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("C:\\Users\\Пользователь\\IdeaProjects\\speech2text_translator\\src\\main\\resources\\config.json"));
//            jsonObject = (JSONObject) obj;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }

    @GetMapping("/homepage")
    public String home(){
        return "main_page";
    }


    @PostMapping ("/login")
    @ResponseBody
    public User login(@RequestParam("id") String id,
                        Model model) throws ExecutionException, InterruptedException {
        User user = userRepo.getUserById(id);
        model.addAttribute("user",user);
        return user;
    }




}
