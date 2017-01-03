package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.bean.HomeBean;
import com.rhg.qf.mvp.base.RxPresenter;
import com.rhg.qf.mvp.model.HomeModel;
import com.rhg.qf.mvp.presenter.contact.HomeContact;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:mvp presenter 测试实现类
 * author：remember
 * time：2016/5/28 17:02
 * email：1013773046@qq.com
 */
public class HomePresenter extends RxPresenter<HomeContact.View<HomeBean>>{
    private HomeModel homeModel;


    public HomePresenter() {
        homeModel = new HomeModel();
    }

    public void getHomeData(String headrestaurants) {
        addSubscription(homeModel.getHomeData(headrestaurants)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, HomeBean>() {
                    @Override
                    public HomeBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<HomeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("RHG", e.getMessage());
                    }

                    @Override
                    public void onNext(HomeBean homeBean) {
                        view.showHomeData(homeBean);
                    }
                }));
    }
}
