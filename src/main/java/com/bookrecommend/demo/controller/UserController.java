package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/user")
    public String index(@RequestParam("user_id") Integer userId, Model model) {

        return "user";
    }
}
