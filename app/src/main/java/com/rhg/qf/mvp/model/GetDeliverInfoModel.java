package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.DeliverInfoBean;
import com.rhg.qf.bean.DeliverOrderNumber;
import com.rhg.qf.constants.AppConstants;
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
                        return Observable.from(deliverInfoBean.getDeliverInfo());
                    }
                }).flatMap(new Func1<DeliverInfoBean.InfoBean, Observable<DeliverInfoBean.InfoBean>>() {
            @Override
            public Observable<DeliverInfoBean.InfoBean> call(final DeliverInfoBean.InfoBean infoBean) {
                return QFoodApiMamager.getInstant().getQFoodApiService().getDeliverOrderNum(AppConstants.DELIVER_ORDER_NUMBER, infoBean.getID())
                        .flatMap(new Func1<DeliverOrderNumber, Observable<DeliverInfoBean.InfoBean>>() {
                            @Override
                            public Observable<DeliverInfoBean.InfoBean> call(DeliverOrderNumber deliverOrderNumber) {
                                if (deliverOrderNumber.getResult() == 0) {
                                    infoBean.setDeliverOrderNum((deliverOrderNumber.getRows().get(0)).getNum());
                                }
                                return Observable.just(infoBean);
                            }
                        });
            }
        });
    }
}
