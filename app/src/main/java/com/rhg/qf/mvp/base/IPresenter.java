package com.rhg.qf.mvp.base;

/**
 * desc:Presenter层统一接口
 * author：remember
 * time：2016/10/25 15:50
 * email：1013773046@qq.com
 */

public interface IPresenter<V> {

    void attachView(V view);

    void detachView();

}
