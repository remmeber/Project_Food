package com.rhg.qf.mvp.presenter.contact;

import com.rhg.qf.bean.GoodsDetailUrlBean;
import com.rhg.qf.mvp.base.IPresenter;
import com.rhg.qf.mvp.base.IView;

/**
 * desc:View Presenter关联接口
 * author：remember
 * time：2016/10/26 14:35
 * email：1013773046@qq.com
 */

public interface GoodsDetailContact {

    interface View<M> extends IView {
        void showContent(M data);
    }

    interface Presenter<V> extends IPresenter<V> {
        void getGoodsInfo(String foodmessage, String foodId);
    }
}
