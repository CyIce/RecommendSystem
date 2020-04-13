package com.bookrecommend.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String FormatDate(Date date, boolean isDate) {
        SimpleDateFormat sdf;
        if (isDate) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        return sdf.format(date);
    }
}
