package com.rhg.qf.mvp.presenter;

import com.rhg.qf.mvp.model.DeliverStateModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribe(new Subscriber<String>() {
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
