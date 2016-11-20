package com.rhg.qf.mvp.model;


import com.rhg.qf.bean.OrderDetailUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import rx.Observable;
import rx.functions.Func1;

/*
 *desc
 *author rhg
 *time 2016/7/10 17:20
 *email 1013773046@qq.com
 */
public class OrderDetailModel {
    public Observable<OrderDetailUrlBean.OrderDetailBean> getOrderDetail(final String orderDetail, String orderId) {
        return QFoodApiMamager.getInstant().getQFoodApiService().getOrderDetail(orderDetail, orderId)
                .flatMap(new Func1<OrderDetailUrlBean, Observable<OrderDetailUrlBean.OrderDetailBean>>() {
                    @Override
                    public Observable<OrderDetailUrlBean.OrderDetailBean> call(final OrderDetailUrlBean orderDetailUrlBean) {
                        return Observable.just(orderDetailUrlBean.getRows().get(0));
                    }
                });
    }
}
