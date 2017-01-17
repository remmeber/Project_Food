package com.rhg.qf.update.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.rhg.qf.update.LibUpgradeInitializer;


/**
 * Caiyuan Huang
 * <p>2016/10/27</p>
 * <p>UI处理工具类</p>
 */
public class UIHandleUtils {
    private static Handler mainHandler;

    private static Handler getMainHandler() {
        if (mainHandler == null) {
            mainHandler = new Handler(Looper.getMainLooper());
        }
        return mainHandler;
    }

    public static void runOnUIThread(Runnable runnable) {
        if (runnable != null) {
            getMainHandler().post(runnable);
        }
    }


    public static String getString(int id) {
        return LibUpgradeInitializer.getContext().getResources().getString(id);
    }

}
