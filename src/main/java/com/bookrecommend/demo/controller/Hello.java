package com.bookrecommend.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Hello {


    @GetMapping("/hello")
    public String hello(@RequestParam("user_id") Integer userId, Model model) {

        model.addAttribute("userId", userId);
        return "hello";
    }

}