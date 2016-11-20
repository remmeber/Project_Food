package com.rhg.qf.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.rhg.qf.constants.AppConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * desc:图片工具
 * author：remember
 * time：2016/6/2 9:39
 * email：1013773046@qq.com
 */
public class ImageUtils {
    public static void showImage(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, new ImageSize(400, 300));
    }

    public static void clearCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static Uri getImageUri(Bitmap bitmap) {

        File file = new File(AppConstants.f_Path);
        if (!file.exists()) {
            file.mkdir();
        }
        File _file = new File(AppConstants.f_Path, DataUtil.getCurrentTime() + ".png");
        FileOutputStream out;
        try {
            out = new FileOutputStream(_file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(_file);
    }
}
