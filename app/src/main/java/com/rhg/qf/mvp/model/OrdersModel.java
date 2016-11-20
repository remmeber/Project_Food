package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.OrderUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * desc:mvp测试实现
 * author：remember
 * time：2016/5/28 17:00
 * email：1013773046@qq.com
 */
public class OrdersModel {

    public Observable<List<OrderUrlBean.OrderBean>> getOrders(String table, String userId, int style) {
        return QFoodApiMamager.getInstant().getQFoodApiService().getOrders(table, userId, String.valueOf(style))
                .flatMap(new Func1<OrderUrlBean, Observable<List<OrderUrlBean.OrderBean>>>() {
                    @Override
                    public Observable<List<OrderUrlBean.OrderBean>> call(final OrderUrlBean orderUrlBean) {
                        return Observable.just(orderUrlBean.getRows());
                    }
                });
    }
}
