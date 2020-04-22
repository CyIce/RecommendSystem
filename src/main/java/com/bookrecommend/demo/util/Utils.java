package com.bookrecommend.demo.util;

import com.bookrecommend.demo.respository.UserRepository;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    // Date装字符串
    public static String Date2String(Date date, boolean isDate) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf;
        if (isDate) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        return sdf.format(date);
    }

    // 字符串日期装Date
    public static Date String2Date(String dateStr, boolean isDate) {
        if (dateStr.equals("")) {
            return null;
        }
        SimpleDateFormat sdf;
        if (isDate) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static double DoubleToFormat(double number) {
        BigDecimal bigDecimal = new BigDecimal(number);

        return bigDecimal.setScale(1, BigDecimal.ROUND_UP).doubleValue();
    }


    public static int SetLoginInfo(Model model, HttpServletRequest request, UserRepository userRepository) {

        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId");
        int userId = 1;
        if (userIdObj != null) {
            userId = Integer.parseInt(userIdObj.toString());
            model.addAttribute("isLogin", true);
            model.addAttribute("user", userRepository.findUserNameAndPhotoByUserId(userId));
            return userId;
        } else {
            model.addAttribute("isLogin", false);
            return 1;
        }

    }
}
