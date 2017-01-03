package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.mvp.model.RestaurantSearchModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, MerchantUrlBean>() {
                    @Override
                    public MerchantUrlBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MerchantUrlBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(MerchantUrlBean merchantBeen) {
                        CommonListModel<MerchantUrlBean.MerchantBean> merchantBeanCommonListModel = new CommonListModel<>();
                        merchantBeanCommonListModel.setRecommendShopBeanEntity(merchantBeen.getRows());
                        restaurantSearchResult.showData(merchantBeanCommonListModel);
                    }
                });
    }
}


