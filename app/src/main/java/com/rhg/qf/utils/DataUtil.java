package com.rhg.qf.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * desc:日期工具类
 * author：remember
 * time：2016/6/11 14:10
 * email：1013773046@qq.com
 */
public class DataUtil {

    public static String getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss", Locale.ENGLISH);
        return dateFormat.format(date);
    }
/*
    // 使用系统当前日期加以调整作为照片的名称
    public static String getDataForImageName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss", Locale.ENGLISH);
        return dateFormat.format(date) + ".jpg";
    }*/
}
