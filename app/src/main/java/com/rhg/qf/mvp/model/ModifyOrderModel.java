package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.BaseBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.mvp.api.QFoodApiService;

import rx.Observable;
import rx.functions.Func1;

/**
 * desc:
 * author：remember
 * time：2016/7/10 23:25
 * email：1013773046@qq.com
 */
public class ModifyOrderModel {
    /**
     * desc:styleOrTable 表示订单类型或者查询的表
     * author：remember
     * time：2016/7/15 22:29
     * email：1013773046@qq.com
     */

    public Observable<String> modifyOrder(String orderId, final String styleOrTable) {
        final QFoodApiService _service = QFoodApiMamager.getInstant().getQFoodApiService();
        if (AppConstants.ORDER_WITHDRAW.equals(styleOrTable) || AppConstants.ORDER_FINISH.equals(styleOrTable)) {
            return _service.modifyOrderState(orderId, styleOrTable)
                    .flatMap(new Func1<BaseBean, Observable<String>>() {
                        @Override
                        public Observable<String> call(final BaseBean baseBean) {
                            return Observable.just("order_modify_success");
                        }
                    });
        }
        if (AppConstants.UPDATE_ORDER_DELIVER.equals(styleOrTable))
            return _service.modifyDeliverOrderState(styleOrTable, orderId).flatMap(new Func1<BaseBean, Observable<String>>() {
                @Override
                public Observable<String> call(final BaseBean baseBean) {
                    if (baseBean.getResult() == 0)
                        return Observable.just("state_delivering_success");
                    return Observable.just("state_error");
                }
            });
        else if (AppConstants.UPDATE_ORDER_PAID.equals(styleOrTable)) {
            return _service.modifyDeliverOrderState(styleOrTable, orderId).flatMap(
                    new Func1<BaseBean, Observable<String>>() {
                        @Override
                        public Observable<String> call(final BaseBean baseBean) {
                            if (baseBean.getResult() == 0)
                                return Observable.just("state_wait_success");
                            return Observable.just("state_error");
                        }
                    }
            );
        } else if (AppConstants.UPDATE_ORDER_WAIT.equals(styleOrTable))
            return _service.modifyDeliverOrderState(styleOrTable, orderId).flatMap(new Func1<BaseBean, Observable<String>>() {
                @Override
                public Observable<String> call(final BaseBean baseBean) {
                    if (baseBean.getResult() == 0)
                        return Observable.just("state_accept_success");
                    return Observable.just("state_error");
                }
            });
        return null;
    }
}
