package com.rhg.qf.utils;

import com.rhg.qf.application.InitApplication;

/**
 * desc: 单位转换工具
 * author：remember
 * time：2016/7/29 23:17
 * email：1013773046@qq.com
 */
public class SizeUtil {
    public static int dip2px(float dpValue) {
        final float scale = InitApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int sp2px(float sp) {
        final float scale = InitApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
