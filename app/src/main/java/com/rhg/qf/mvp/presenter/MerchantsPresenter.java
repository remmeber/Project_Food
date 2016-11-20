package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.mvp.model.MerchantsModel;
import com.rhg.qf.mvp.view.BaseView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:mvp presenter 测试实现类
 * author：remember
 * time：2016/5/28 17:02
 * email：1013773046@qq.com
 */
public class MerchantsPresenter {
    BaseView getMerchantsPresenter;
    MerchantsModel merchantsModel;

    public MerchantsPresenter(BaseView baseView) {
        getMerchantsPresenter = baseView;
        merchantsModel = new MerchantsModel();
    }

    public void getMerchants(String table, int page) {
        merchantsModel.getMerchants(table, page).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<MerchantUrlBean.MerchantBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<MerchantUrlBean.MerchantBean> merchantBeanList) {
                        getMerchantsPresenter.showData(merchantBeanList);
                    }
                });
    }
}
