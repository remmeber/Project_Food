package com.rhg.qf.bean;

/*
 *desc 数据模型的基类
 *author rhg
 *time 2016/12/27 16:13
 *email 1013773046@qq.com
 */

import java.util.List;

public interface IBaseModel<R> {
    List<R> getEntity();
//    void setEntity();
}
