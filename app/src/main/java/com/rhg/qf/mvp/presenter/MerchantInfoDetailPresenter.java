package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.MerchantInfoDetailUrlBean;
import com.rhg.qf.mvp.model.MerchantInfoDetailModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
        getMerchantInfoModel.getMerchantInfo(merchantDetail, merchantId).observeOn(AndroidSchedulers.mainThread())
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
