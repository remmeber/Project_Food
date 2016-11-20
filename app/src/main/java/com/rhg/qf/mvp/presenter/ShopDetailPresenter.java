package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.ShopDetailLocalModel;
import com.rhg.qf.mvp.model.ShopDetailModel;
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
public class ShopDetailPresenter {
    BaseView testView;
    ShopDetailModel shopDetailModel;

    public ShopDetailPresenter(BaseView baseView) {
        testView = baseView;
        shopDetailModel = new ShopDetailModel();
    }

    public void getShopDetail(String table, String merchantId) {
        shopDetailModel.getShopDetail(table, merchantId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ShopDetailLocalModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ShopDetailLocalModel shopDetailLocalModel) {
                        testView.showData(shopDetailLocalModel);
                    }
                });
    }
}
