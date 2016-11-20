package com.rhg.qf.mvp.presenter;

import com.rhg.qf.mvp.model.DIYOrderModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:
 * author：remember
 * time：2016/7/11 2:11
 * email：1013773046@qq.com
 */
public class DIYOrderPresenter {
    BaseView diyOrderView;
    DIYOrderModel commitDIYOrderModel;

    public DIYOrderPresenter(BaseView diyOrderView) {
        this.diyOrderView = diyOrderView;
        commitDIYOrderModel = new DIYOrderModel();
    }

    public void commitDIYOrder(String content) {
        commitDIYOrderModel.commitDIYOrder(content).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        diyOrderView.showData(s);
                    }
                });
    }
}
