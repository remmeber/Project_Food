package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.HotFoodUrlBean;
import com.rhg.qf.mvp.model.HotFoodModel;
import com.rhg.qf.mvp.view.BaseView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:mvp中获取热销单品presenter
 * author：remember
 * time：2016/5/28 17:01
 * email：1013773046@qq.com
 */
public class HotFoodPresenter {
    BaseView baseView;
    HotFoodModel hotFoodModel;

    public HotFoodPresenter(BaseView baseView) {
        this.baseView = baseView;
        hotFoodModel = new HotFoodModel();
    }

    public void getHotFoods(final String hotFood, int orderType, String key) {
        hotFoodModel.getHotFood(hotFood, orderType, key).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<HotFoodUrlBean.HotFoodBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<HotFoodUrlBean.HotFoodBean> hotGoodsBeen) {
                        baseView.showData(hotGoodsBeen);
                    }
                });
    }
}
