package com.rhg.qf.mvp.model;


import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import rx.Observable;

/*
 *desc
 *author rhg
 *time 2016/7/7 20:31
 *email 1013773046@qq.com
 */
public class RestaurantSearchModel {
    public Observable<MerchantUrlBean> getSearchRestaurants(String searchRestaurants,
                                                            String searchContent,/*utf-8*/
                                                            int style) {
        return QFoodApiMamager.getInstant().getQFoodApiService().getRestaurantSearchResult(searchRestaurants, searchContent, String.valueOf(style))

                /*.flatMap(new Func1<MerchantUrlBean, Observable<List<MerchantUrlBean.MerchantBean>>>() {
                    @Override
                    public Observable<List<MerchantUrlBean.MerchantBean>> call(final MerchantUrlBean merchantUrlBean) {
                        return Observable.create(new Observable.OnSubscribe<List<MerchantUrlBean.MerchantBean>>() {
                            @Override
                            public void call(Subscriber<? super List<MerchantUrlBean.MerchantBean>> subscriber) {
                                Log.i("RHG", "success:" + merchantUrlBean.getTotal());
                                subscriber.onNext(merchantUrlBean.getRows());
                            }
                        });
                    }
                })*/;
    }
}
