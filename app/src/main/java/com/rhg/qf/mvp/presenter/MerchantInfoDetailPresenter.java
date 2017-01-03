package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.MerchantInfoDetailUrlBean;
import com.rhg.qf.mvp.model.MerchantInfoDetailModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/*
 *desc
 *author rhg
 *time 2016/7/10 15:41
 *email 1013773046@qq.com
 */
public class MerchantInfoDetailPresenter {
    BaseView getMerchantInfoView;
    MerchantInfoDetailModel getMerchantInfoModel;

    public MerchantInfoDetailPresenter(BaseView getMerchantInfoView) {
        this.getMerchantInfoView = getMerchantInfoView;
        getMerchantInfoModel = new MerchantInfoDetailModel();
    }

    public void getMerchantInfo(final String merchantDetail, String merchantId) {
        getMerchantInfoModel.getMerchantInfo(merchantDetail, merchantId)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, MerchantInfoDetailUrlBean.MerchantInfoDetailBean>() {
                    @Override
                    public MerchantInfoDetailUrlBean.MerchantInfoDetailBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MerchantInfoDetailUrlBean.MerchantInfoDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MerchantInfoDetailUrlBean.MerchantInfoDetailBean merchantInfoDetailBean) {
                        getMerchantInfoView.showData(merchantInfoDetailBean);
                    }
                });
    }
}
