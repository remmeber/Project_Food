package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.DeliverOrderUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * desc:跑腿员订单model
 * author：remember
 * time：2016/7/10 11:14
 * email：1013773046@qq.com
 */
public class DeliverOrderModel {

    public Observable<List<DeliverOrderUrlBean.DeliverOrderBean>> getDeliverOrder(String deliverOrder,
                                                                                  String deliverId) {
        return QFoodApiMamager.getInstant().getQFoodApiService().getDeliverOrder(deliverOrder, deliverId)
                .flatMap(new Func1<DeliverOrderUrlBean, Observable<List<DeliverOrderUrlBean.DeliverOrderBean>>>() {
                    @Override
                    public Observable<List<DeliverOrderUrlBean.DeliverOrderBean>> call(final DeliverOrderUrlBean deliverOrderUrlBean) {
                        return Observable.just(deliverOrderUrlBean.getRows());
                    }
                });
    }
}
