package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.HotFoodUrlBean;
import com.rhg.qf.mvp.model.HotFoodSearchModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<HotFoodUrlBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HotFoodUrlBean hotFoodSearchBeen) {
                        hotFoodSearchResult.showData(hotFoodSearchBeen);
                    }

                });
    }
}


