package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.MerchantInfoDetailUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import rx.Observable;
import rx.functions.Func1;

/*
 *desc
 *author rhg
 *time 2016/7/10 15:36
 *email 1013773046@qq.com
 */
public class MerchantInfoDetailModel {
    public Observable<MerchantInfoDetailUrlBean.MerchantInfoDetailBean> getMerchantInfo(String merchantDetail, String merchantId) {
        return QFoodApiMamager.getInstant().getQFoodApiService().getMerchantInfo(merchantDetail, merchantId)
                .flatMap(new Func1<MerchantInfoDetailUrlBean,
                        Observable<MerchantInfoDetailUrlBean.MerchantInfoDetailBean>>() {
                    @Override
                    public Observable<MerchantInfoDetailUrlBean.MerchantInfoDetailBean> call(final MerchantInfoDetailUrlBean
                                                                                                     merchantInfoDetailUrlBean) {
                        return Observable.just(merchantInfoDetailUrlBean.getRows().get(0));
                    }
                });
    }
}
