package com.bookrecommend.demo.util;

import com.bookrecommend.demo.respository.UserRepository;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
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


    public static int GetUserId(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj != null) {
            return Integer.parseInt(userIdObj.toString());
        }
        return -1;

    }

    // 从本地读取Json文件并解析
    public static String getJson() {
        String jsonStr = "";
        try {
            File file = new File("/Users/cyice/Desktop/book.json");
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), "Utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (Exception e) {
            return null;
        }
    }


    //    下载图片
    public static void download(String urlString, int i) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        String filename = "/Users/cyice/Desktop/picture/" + i + ".jpg";  //下载路径及下载图片名称
        File file = new File(filename);
        FileOutputStream os = new FileOutputStream(file, true);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        System.out.println(i);
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }
}
