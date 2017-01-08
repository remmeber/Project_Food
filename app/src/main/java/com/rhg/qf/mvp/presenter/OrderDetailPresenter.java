package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.bean.OrderDetailUrlBean;
import com.rhg.qf.mvp.model.OrderDetailModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/*
 *desc
 *author rhg
 *time 2016/7/10 19:18
 *email 1013773046@qq.com
 */
public class OrderDetailPresenter {
    BaseView getOrderDetailView;
    OrderDetailModel getOrderDetailModel;

    public OrderDetailPresenter(BaseView getOrderDetailView) {
        this.getOrderDetailView = getOrderDetailView;
        getOrderDetailModel = new OrderDetailModel();
    }

    public void getOrderDetail(final String orderDetail, String orderId) {
        getOrderDetailModel.getOrderDetail(orderDetail, orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, OrderDetailUrlBean.OrderDetailBean>() {
                    @Override
                    public OrderDetailUrlBean.OrderDetailBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<OrderDetailUrlBean.OrderDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("RHG", e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailUrlBean.OrderDetailBean orderDetailUrlBean) {
                        Log.i("RHG", "SUCCESS");
                        getOrderDetailView.showData(orderDetailUrlBean);
                    }
                });
    }
}
