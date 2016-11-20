package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.DeliverStateBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import rx.Observable;
import rx.functions.Func1;

/**
 * desc:
 * author：remember
 * time：2016/7/10 10:55
 * email：1013773046@qq.com
 */
public class DeliverStateModel {

    public Observable<String> getDeliverState(String orderStyle, String orderId) {
        return QFoodApiMamager.getInstant().getQFoodApiService().getDeliverState(orderStyle, orderId)
                .flatMap(new Func1<DeliverStateBean, Observable<String>>() {
                    @Override
                    public Observable<String> call(final DeliverStateBean deliverStateBean) {
                        return Observable.just(deliverStateBean.getRows().get(0).getStyle());
                    }
                });
    }
}
