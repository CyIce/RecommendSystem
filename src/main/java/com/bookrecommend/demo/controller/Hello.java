package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.entity.User;
import com.bookrecommend.demo.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String hello(Model model) {

        User user = userRepository.getOne(1);

        model.addAttribute("name", "thymeleaf");
        model.addAttribute("user", user);
        return "hello";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }
}