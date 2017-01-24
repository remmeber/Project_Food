package com.rhg.qf.update.utils;

import android.os.Environment;


import com.rhg.qf.update.LibUpgradeInitializer;

import java.io.File;

/**
 * Caiyuan Huang
 * <p>2016/10/28</p>
 * <p>文件工具类</p>
 */
public class FileUtils {

    /**
     * 获取文件保存地址
     *
     * @param fileName    文件下载地址
     * @param fileSuffix 文件后缀
     * @return 文件保存路径
     */
    public static String getFileSavePath(String fileName, String fileSuffix) {
        String cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // /sdcard/Android/data/<application package>/cache
            cacheDir = LibUpgradeInitializer.getContext().getExternalCacheDir()
                    .getAbsolutePath();
        } else {
            // /data/data/<application package>/file
            cacheDir = LibUpgradeInitializer.getContext().getFilesDir().getAbsolutePath();
        }
//        String MD5 = MD5Utils.get32BitsMD5(fileName);
        return cacheDir + File.separator + fileName + fileSuffix;

    }
}
