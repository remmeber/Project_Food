package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.GoodsDetailUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * desc:Mvp中获取数据详情的实现类
 * author：remember
 * time：2016/5/28 16:56
 * email：1013773046@qq.com
 */
public class GoodsDetailModel {
    public Observable<GoodsDetailUrlBean.GoodsDetailBean> getGoodsDetail(String foodmessage, String foodId) {
        return QFoodApiMamager.getInstant()
                .getQFoodApiService().getGoodsDetail(foodmessage, Integer.valueOf(foodId))
                .flatMap(new Func1<GoodsDetailUrlBean, Observable<GoodsDetailUrlBean.GoodsDetailBean>>() {
                    @Override
                    public Observable<GoodsDetailUrlBean.GoodsDetailBean> call(
                            final GoodsDetailUrlBean goodsDetailUrlBean) {
                        return Observable.create(new Observable.OnSubscribe<GoodsDetailUrlBean.GoodsDetailBean>() {
                            @Override
                            public void call(Subscriber<? super GoodsDetailUrlBean.GoodsDetailBean> subscriber) {
                                subscriber.onNext(goodsDetailUrlBean.getRows().get(0));
                            }
                        });
                    }
                });
    }
}
