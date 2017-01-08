package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.HomeBean;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.mvp.model.MerchantsModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                .onErrorReturn(new Func1<Throwable, CommonListModel<MerchantUrlBean.MerchantBean>>() {
                    @Override
                    public CommonListModel<MerchantUrlBean.MerchantBean> call(Throwable throwable) {
                        if (throwable.getClass().getName().contains("RuntimeException")) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable.getClass().getName().contains("SSLHandshakeException")) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CommonListModel<MerchantUrlBean.MerchantBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CommonListModel<MerchantUrlBean.MerchantBean> merchantBeanList) {
                        getMerchantsPresenter.showData(merchantBeanList);
                    }
                });
    }
}
