package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.HotFoodUrlBean;
import com.rhg.qf.mvp.model.HotFoodModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                .onErrorReturn(new Func1<Throwable, List<HotFoodUrlBean.HotFoodBean>>() {
                    @Override
                    public List<HotFoodUrlBean.HotFoodBean> call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribe(new Subscriber<List<HotFoodUrlBean.HotFoodBean>>() {
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
