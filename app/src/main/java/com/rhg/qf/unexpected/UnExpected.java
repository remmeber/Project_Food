package com.rhg.qf.unexpected;

/*
 *desc
 *author rhg
 *time 2016/12/8 16:55
 *email 1013773046@qq.com
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.rhg.qf.application.InitApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/*
 *desc 异常捕获类
 *author rhg
 *time 2016/12/8 22:18
 *email 1013773046@qq.com
 */

public class UnExpected implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "RHG";
    // 系统默认的UncaughtException
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Context context;
    private Map<String, String> infos = new HashMap<>();

    public UnExpected(Context context) {
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * 发生异常时转入该函数进行处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.i(TAG, "BUG , " + (ex == null));
        if (handleException(ex) && defaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, e.toString());
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable throwable) {
        Log.i(TAG, "Handle BUG");
        if (throwable == null) {
            return false;
        }
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "程序出现异常，即将退出.....", Toast.LENGTH_SHORT)
                        .show();
                Looper.loop();
            }
        }.start();
        collectDeviceInfo(context);
        saveInfo(throwable);
        return true;
    }

    /**
     * 收集设备信息
     *
     * @param ctx 上下文信息
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到本地
     *
     * @param ex 抛出的异常信息
     */
    private void saveInfo(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key);
            sb.append("=");
            sb.append(value);
            sb.append("\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String path = context.getFilesDir().getPath() + "/" + InitApplication.fileName;
            File dir = new File(path);
            if (!dir.exists()) {
                if (dir.createNewFile()) {

                }
            }
            FileOutputStream fos = new FileOutputStream(path, false);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
    }
}
