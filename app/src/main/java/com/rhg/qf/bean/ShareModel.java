package com.rhg.qf.bean;

import com.umeng.socialize.media.UMImage;

/**
 * desc:
 * author：remember
 * time：2016/6/17 15:37
 * email：1013773046@qq.com
 */
public class ShareModel {
    private String title;
    private String content;
    private UMImage imageMedia;

    public ShareModel(String title, String content, UMImage imageMedia) {
        this.title = title;
        this.content = content;
        this.imageMedia = imageMedia;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UMImage getImageMedia() {
        return imageMedia;
    }
}
