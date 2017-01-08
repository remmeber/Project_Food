package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.mvp.model.ModifyOrderModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:
 * author：remember
 * time：2016/7/10 23:29
 * email：1013773046@qq.com
 */
public class ModifyOrderPresenter {
    private BaseView modifyUserOrderView;
    private ModifyOrderModel modifyOrderModel;

    public ModifyOrderPresenter(BaseView modifyUserOrderView) {
        this.modifyUserOrderView = modifyUserOrderView;
        modifyOrderModel = new ModifyOrderModel();
    }

    public void modifyUserOrDeliverOrderState(String orderId, String style) {
        modifyOrderModel.modifyOrder(orderId, style).observeOn(AndroidSchedulers.mainThread())
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
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("RHG", "onError:" + e.getMessage());

                    }

                    @Override
                    public void onNext(String s) {
                        modifyUserOrderView.showData(s);
                    }
                });
    }

}
