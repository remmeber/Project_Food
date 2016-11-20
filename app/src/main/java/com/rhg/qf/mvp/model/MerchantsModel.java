package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.utils.AccountUtil;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * desc:mvp测试实现
 * author：remember
 * time：2016/5/28 17:00
 * email：1013773046@qq.com
 */
public class MerchantsModel {
    public Observable<List<MerchantUrlBean.MerchantBean>> getMerchants(String table, int page) {
//        QFoodApiService qFoodApiService = QFoodApiMamager.getInstant().getQFoodApiService();
        return /*Observable.zip(qFoodApiService.getHeadMerchant("toprestaurants"),
                qFoodApiService.getBodyMerchants(table, page,
                        AccountUtil.getInstance().getLongitude(),
                        AccountUtil.getInstance().getLatitude()),
                new Func2<HeadMerchantUrlBean, MerchantUrlBean, List<MerchantUrlBean.MerchantBean>>() {
                    @Override
                    public List<MerchantUrlBean.MerchantBean> call(HeadMerchantUrlBean headMerchantUrlBean,
                                                                   MerchantUrlBean merchantUrlBean) {
                        List<MerchantUrlBean.MerchantBean> _merchantsList = new ArrayList<>();
                        _merchantsList.addAll(headMerchantUrlBean.getRows());
                        _merchantsList.addAll(merchantUrlBean.getRows());
                        return _merchantsList;
                    }
                });*/
        QFoodApiMamager.getInstant().getQFoodApiService().getBodyMerchants(table, page,
                AccountUtil.getInstance().getLongitude(),
                AccountUtil.getInstance().getLatitude())
                .flatMap(new Func1<MerchantUrlBean, Observable<List<MerchantUrlBean.MerchantBean>>>() {
                    @Override
                    public Observable<List<MerchantUrlBean.MerchantBean>> call(final MerchantUrlBean merchantUrlBean) {
                        return Observable.just(merchantUrlBean.getRows());
                    }
                });
    }
}