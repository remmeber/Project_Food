package com.rhg.qf.mvp.base;

/**
 * desc:View层统一接口
 * author：remember
 * time：2016/10/25 15:48
 * email：1013773046@qq.com
 */

public interface IView<T> {
//    void setPresenter(T presenter);

    void showError(String error);
}
