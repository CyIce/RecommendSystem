//package com.bookrecommend.demo.controller;
//
//import com.bookrecommend.demo.entity.Press;
//import com.bookrecommend.demo.respository.PressRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/press")
//public class PressController {
//
//    @Autowired
//    private PressRepository pressRepository;
//
//    @GetMapping()
//    public Press getPress(@RequestParam(value = "press_id") Integer pressId) {
//        return pressRepository.findPressById(pressId);
//    }
//}
