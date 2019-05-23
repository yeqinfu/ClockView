package com.example.myapplication;

import java.util.Calendar;

/**
 * Created by yeqinfu on 23/05/2019
 */
public class CommonNumber {
    private static String[] numberArray = {
            "一",
            "二",
            "三",
            "四",
            "五",
            "六",
            "七",
            "八",
            "九",
            "十",
            "十一",
            "十二",
            "十三",
            "十四",
            "十五",
            "十六",
            "十七",
            "十八",
            "十九",
            "二十",
            "二十",
            "二十一",
            "二十二",
            "二十三",
            "二十四",
            "二十五",
            "二十六",
            "二十七",
            "二十八",
            "二十九",
            "三十",
            "三十一",
            "三十二",
            "三十三",
            "三十四",
            "三十五",
            "三十六",
            "三十七",
            "三十八",
            "三十九",
            "四十",
            "四十一",
            "四十二",
            "四十三",
            "四十四",
            "四十五",
            "四十六",
            "四十七",
            "四十八",
            "四十九",
            "五十",
            "五十一",
            "五十二",
            "五十三",
            "五十四",
            "五十五",
            "五十六",
            "五十七",
            "五十八",
            "五十九",
            "六十"

    };
    static String[] hours = new String[12];
    static String[] months = new String[12];
    static String[] minutes = new String[60];
    static String[] seconds = new String[60];

    static {
        for (int i = 0; i < hours.length; i++) {
            hours[i] = numberArray[i] + "点";
        }

        for (int i = 0; i < months.length; i++) {
            months[i] = numberArray[i] + "月";
        }
        minutes[0] = "零分";
        for (int i = 1; i < minutes.length; i++) {
            minutes[i] = numberArray[i] + "分";
        }
        seconds[0] = "零秒";
        for (int i = 1; i < seconds.length; i++) {
            seconds[i] = numberArray[i] + "秒";
        }


    }

    public static String[] getSeconds() {
        return seconds;
    }

    public static String[] getMinutes() {
        return minutes;
    }

    public static String[] getHours() {
        return hours;
    }

    /**
     * 获取月份数组
     *
     * @return
     */
    public static String[] getDays() {
        return getDays(null);
    }

    public static String[] getDays(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        DateUtils du = new DateUtils();

        int dayOfMonth =  du.getDays(du.getYear(), du.getMonth());
        String[] result = new String[dayOfMonth];
        for (int i = 0; i < result.length; i++) {
            result[i] = numberArray[i] + "日";
        }

        return result;
    }

    /**
     * 月份数组
     *
     * @return
     */
    public static String[] getMonths() {
        return months;
    }

}
