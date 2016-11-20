package com.rhg.qf.bean;

/**
 * desc:主页列表的尾类型
 * author：remember
 * time：2016/5/28 16:31
 * email：1013773046@qq.com
 */
public class FooterTypeModel {
    private String text;
    private int color;

    public FooterTypeModel(String text, int color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
