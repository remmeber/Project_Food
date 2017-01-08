package com.rhg.qf.bean;

/*
 *desc 用于保存加载ViewHolder的类
 *author rhg
 *time 2016/12/27 21:11
 *email 1013773046@qq.com
 */

public class InflateModel {
    private Class<?>[] clazz;
    private final Object[] param;

    public InflateModel(Class<?>[] clazz, Object[] param) {
        this.clazz = clazz;
        this.param = param;
    }

    public Class<?>[] getClazz() {
        return clazz;
    }

    public Object[] getParam() {
        return param;
    }
}
