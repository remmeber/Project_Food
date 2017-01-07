package com.rhg.qf.update;

/*
 *desc 增量更新类
 *author rhg
 *time 2017/1/7 22:42
 *email 1013773046@qq.com
 */
public class BsDiff {

    static {
        try {
            System.loadLibrary("bsdiff_utils");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static native int diff(String oldPath, String newPath, String patchPath);

    public static native int patch(String oldPath, String newPath, String patchPath);
}
