package com.bookrecommend.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookrecommend.demo.Data.BookOnly;
import com.bookrecommend.demo.Data.UserOnly;
import com.bookrecommend.demo.entity.Recommend;
import com.bookrecommend.demo.entity.User;
import com.bookrecommend.demo.respository.BookRepository;
import com.bookrecommend.demo.respository.RecommendRepository;
import com.bookrecommend.demo.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RecommendRepository recommendRepository;

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


    @PostMapping(value = "/user/register")
//    @ResponseBody
    public String register(@RequestBody JSONObject json) {


        String name = json.getString("name");
        String email = json.getString("email");
        String password = json.getString("password");
        boolean gender = json.getBoolean("gender");
        String photo = "http://127.0.0.1:8081/img/userPhoto/1.jpg";
        String info = "注册逻辑";
        log.info(info);
        log.info("name为：" + name);
        log.info("email为：" + email);
        log.info("password为：" + password);
        log.info("gender为：" + gender);

        User user = new User(name, email, password, gender, photo, new Date());
        userRepository.save(user);

        List<BookOnly> books = bookRepository.findBookIdOrderByWeekHot().subList(0, 10);
        for (BookOnly b : books) {
            Recommend recommend = new Recommend();
            recommend.setUserId(user.getId());
            recommend.setBookId(b.getId());
            recommend.setValue(10);
            recommend.setDate(new Date());
            recommendRepository.save(recommend);
        }


        log.info("注册成功");

        return "login";
    }

    @GetMapping(value = "/register")
    public String register() {

        return "register";
    }

    @GetMapping(value = "/existemail")
    @ResponseBody
    public boolean existEmail(@RequestParam("email") String email) {
        UserOnly user = userRepository.findUserByEmail(email);
        return user == null;
    }
}
