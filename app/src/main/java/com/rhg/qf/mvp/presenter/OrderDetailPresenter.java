package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.bean.OrderDetailUrlBean;
import com.rhg.qf.mvp.model.OrderDetailModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
        getOrderDetailModel.getOrderDetail(orderDetail, orderId).observeOn(AndroidSchedulers.mainThread())
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
