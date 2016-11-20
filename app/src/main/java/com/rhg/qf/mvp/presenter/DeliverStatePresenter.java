package com.rhg.qf.mvp.presenter;

import com.rhg.qf.mvp.model.DeliverStateModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:
 * author：remember
 * time：2016/7/10 10:55
 * email：1013773046@qq.com
 */
public class DeliverStatePresenter {
    BaseView deliverStateView;
    DeliverStateModel deliverStateModel;

    public DeliverStatePresenter(BaseView deliverStateView) {
        this.deliverStateView = deliverStateView;
        deliverStateModel = new DeliverStateModel();
    }

    public void getDeliverState(String orderStyle, String orderId) {
        deliverStateModel.getDeliverState(orderStyle, orderId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(/*new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        deliverStateView.showData(s);
                    }
                }*/new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        deliverStateView.showData(s);
                    }
                });
    }
}
