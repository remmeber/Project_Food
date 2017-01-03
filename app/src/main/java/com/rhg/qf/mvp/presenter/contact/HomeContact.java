package com.rhg.qf.mvp.presenter.contact;

import com.rhg.qf.mvp.base.IView;

/*
 *desc 主页View presenter关联接口
 *author rhg
 *time 2017/1/2 19:59
 *email 1013773046@qq.com
 */

public interface HomeContact {
    interface View<M> extends IView {
        void showHomeData(M data);
    }
}
