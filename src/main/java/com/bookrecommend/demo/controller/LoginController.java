package com.bookrecommend.demo.controller;

import com.bookrecommend.demo.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/user/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
        String info = "登录逻辑";
        log.info(info);

        if (userRepository.existsUserByEmailAndPassword(email, password)) {
            Integer userId = userRepository.findUserByEmail(email).getId();

            request.getSession().setAttribute("userInfo", email + " - " + password);
            request.getSession().setAttribute("userId", userId);
            return "redirect:/index";
        } else {
            info = "登录失败";
            return "login";
        }

    }

    @GetMapping(value = "/user/logout")
    public String loginOut(HttpServletRequest request) {
        String info = "登出操作";
        log.info(info);
        HttpSession session = request.getSession();

        // 将用户信息从session中删除
        session.removeAttribute("userInfo");
        session.removeAttribute("userId");

        Object userInfo = session.getAttribute("userInfo");
        if (userInfo == null) {
            info = "登出成功";
        } else {
            info = "登出失败";
        }
        log.info(info);

        return "redirect:/index";
    }
}
