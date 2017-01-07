package com.rhg.qf.utils;

/*
 *desc Dialog工具类
 *author rhg
 *time 2016/12/11 10:42
 *email 1013773046@qq.com
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.easemob.easeui.widget.EaseAlertDialog;

public class DialogUtil {
    private static ProgressDialog pd;

    public static void showDialog(Context context, String msg) {
        pd = ProgressDialog.show(context, "提示", msg);
    }

    public static void cancelDialog() {
        if (pd != null)
            pd.cancel();
    }

    public static boolean isShow() {
        return pd != null && pd.isShowing();
    }
}
