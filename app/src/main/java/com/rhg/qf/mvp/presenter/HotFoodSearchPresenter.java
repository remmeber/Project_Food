package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.HotFoodUrlBean;
import com.rhg.qf.mvp.model.HotFoodSearchModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/*
 *desc 热销单品搜索presenter
 *author rhg
 *time 2016/7/7 20:26
 *email 1013773046@qq.com
 */
public class HotFoodSearchPresenter {
    BaseView hotFoodSearchResult;
    HotFoodSearchModel hotFoodSearchModel;

    public HotFoodSearchPresenter(BaseView hotFoodSearchResult) {
        this.hotFoodSearchResult = hotFoodSearchResult;
        hotFoodSearchModel = new HotFoodSearchModel();
    }

    public void getSearchHotFood(String searchRestaurants,/*hotfood*/
                                 String searchContent,/*hot_food_key utf-8编码*/
                                 int order) {
        hotFoodSearchModel.getSearchHotFood(searchRestaurants, searchContent, order)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, HotFoodUrlBean>() {
                    @Override
                    public HotFoodUrlBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())

                .subscribe(new Subscriber<HotFoodUrlBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HotFoodUrlBean hotFoodSearchBeen) {
                        CommonListModel<HotFoodUrlBean.HotFoodBean> hotFoodModel = new CommonListModel<HotFoodUrlBean.HotFoodBean>();
                        hotFoodModel.setRecommendShopBeanEntity(hotFoodSearchBeen.getRows());
                        hotFoodSearchResult.showData(hotFoodModel);
                    }

                });
    }
}


