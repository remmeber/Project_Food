package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.mvp.model.RestaurantSearchModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
 *desc 店铺搜索presenter
 *author rhg
 *time 2016/7/7 20:26
 *email 1013773046@qq.com
 */
public class RestaurantSearchPresenter {
    BaseView restaurantSearchResult;
    RestaurantSearchModel restaurantSearchModel;

    public RestaurantSearchPresenter(BaseView restaurantSearchResult) {
        this.restaurantSearchResult = restaurantSearchResult;
        restaurantSearchModel = new RestaurantSearchModel();
    }

    public void getSearchRestaurant(String searchRestaurants,
                                    String searchContent,
                                    int style) {
        restaurantSearchModel.getSearchRestaurants(searchRestaurants, searchContent, style)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<MerchantUrlBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MerchantUrlBean merchantBeen) {
                        restaurantSearchResult.showData(merchantBeen);
                    }
                });
    }
}


