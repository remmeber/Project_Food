package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.DeliverInfoBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.utils.AccountUtil;

import rx.Observable;
import rx.functions.Func1;

/**
 * desc:跑腿员信息完善Model
 * author：remember
 * time：2016/7/15 0:26
 * email：1013773046@qq.com
 */
public class GetDeliverInfoModel {
    public Observable<DeliverInfoBean.InfoBean> getDeliverInfo(String deliverTable) {
        String user_id = AccountUtil.getInstance().getUserID();
        return QFoodApiMamager.getInstant().getQFoodApiService().getDeliverInfo(deliverTable, user_id).
                flatMap(new Func1<DeliverInfoBean, Observable<DeliverInfoBean.InfoBean>>() {
                    @Override
                    public Observable<DeliverInfoBean.InfoBean> call(final DeliverInfoBean deliverInfoBean) {
                        return Observable.just(deliverInfoBean.getDeliverInfo().get(0));
                    }
                });
    }
}
