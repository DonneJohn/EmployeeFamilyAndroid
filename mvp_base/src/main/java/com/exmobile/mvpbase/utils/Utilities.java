package com.exmobile.mvpbase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thanatos on 15/12/25.
 */
public class Utilities {


    public static String dateFormat(String strdate) throws ParseException {
        if (isEmpty(strdate)) return null;

        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        long timestamp = System.currentTimeMillis() - new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).parse(strdate).getTime();
        if (0 <= timestamp && timestamp < 60 * 60 * 1000) {
            // 几分钟之前
            return timestamp / (60 * 1000) + "分钟之前";
        } else if (60 * 60 * 1000 <= timestamp && timestamp < 24 * 60 * 60 * 1000) {
            // 几小时之前
            return timestamp / (60 * 60 * 1000) + "小时之前";
        } else if (24 * 60 * 60 * 1000 <= timestamp && timestamp < (long) 30 * 24 * 60 * 60 * 1000) {
            // 几天前
            return timestamp / (24 * 60 * 60 * 1000) + "天之前";
        } else if ((long) 31 * 24 * 60 * 60 * 1000 < timestamp && timestamp < (long) 12 * 30 * 24 * 60 * 60 * 1000) {
            // 几个月之前
            return timestamp / ((long) 30 * 24 * 60 * 60 * 1000) + "月之前";
        } else {
            return strdate;
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static int toInt(String str, int d) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            return d;
        }
    }

    public static long toLong(String str, long d) {
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            return d;
        }
    }

    public static boolean isImgUrl(String url) {
        return !isEmpty(url) && Pattern.compile(".*?(gif|jpeg|png|jpg|bmp)").matcher(url).matches();
    }

    /**
     * 验证一个号码是否为手机号码
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhone(String phoneNum) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    public static String dateFormat2(String strdate) {

        return strdate.replace('/', '-');
    }

    public static String transTag(String tag) {

        return tag.replace("-", "");
    }

    /**
     * 字符串日期转换成中文格式日期
     *
     * @param date 字符串日期 yyyy/MM/dd
     * @return yyyy年MM月dd日
     * @throws Exception
     */
    public static String dateToChinaDate(String date) {
        String result = date.split(" ")[0];
        String[] chinaDate = result.split("/");
        if (chinaDate.length == 3) {
            result = chinaDate[0] + "年" + chinaDate[1] + "月" + chinaDate[2] + "日";

        }
        return result;
    }
}
