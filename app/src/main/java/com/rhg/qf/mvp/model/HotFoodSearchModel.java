package com.rhg.qf.mvp.model;


import com.rhg.qf.bean.HotFoodUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.utils.AccountUtil;

import rx.Observable;

/*
 *desc
 *author rhg
 *time 2016/7/7 20:31
 *email 1013773046@qq.com
 */
public class HotFoodSearchModel {
    public Observable<HotFoodUrlBean> getSearchHotFood(String searchHotFood,
                                                       final String searchContent,
                                                       int order) {
        String X = AccountUtil.getInstance().getLongitude();
        String Y = AccountUtil.getInstance().getLatitude();
        return QFoodApiMamager.getInstant().getQFoodApiService().getHotGoodsForSearch(searchHotFood,
                searchContent,
                String.valueOf(order),
                X,
                Y);
               /* .flatMap(new Func1<HotFoodSearchUrlBean, Observable<List<HotFoodSearchUrlBean.HotFoodSearchBean>>>() {
                    @Override
                    public Observable<List<HotFoodSearchUrlBean.HotFoodSearchBean>> call(final HotFoodSearchUrlBean hotFoodSearchUrlBean) {
                        return Observable.create(new Observable.OnSubscribe<List<HotFoodSearchUrlBean.HotFoodSearchBean>>() {
                            @Override
                            public void call(Subscriber<? super List<HotFoodSearchUrlBean.HotFoodSearchBean>> subscriber) {
                                subscriber.onNext(hotFoodSearchUrlBean.getRows());
                            }
                        });
                    }
                })*/
    }
}
