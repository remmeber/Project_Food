package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.HomeBean;
import com.rhg.qf.mvp.model.HomeModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:mvp presenter 测试实现类
 * author：remember
 * time：2016/5/28 17:02
 * email：1013773046@qq.com
 */
public class HomePresenter {
    BaseView testView;
    HomeModel homeModel;

    public HomePresenter(BaseView baseView) {
        testView = baseView;
        homeModel = new HomeModel();
    }

    public void getHomeData(String headrestaurants) {
        homeModel.getHomeData(headrestaurants).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<HomeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HomeBean homeBean) {
                        testView.showData(homeBean);
                    }
                });
    }
}
