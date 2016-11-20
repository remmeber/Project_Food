package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.BaseBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.utils.AccountUtil;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * desc:自主点餐
 * author：remember
 * time：2016/7/11 2:08
 * email：1013773046@qq.com
 */
public class DIYOrderModel {
    public Observable<String> commitDIYOrder(String content) {
        String userId = AccountUtil.getInstance().getUserID();
        return QFoodApiMamager.getInstant().getQFoodApiService().diyOrderFood(userId, content)
                .flatMap(new Func1<BaseBean, Observable<String>>() {
                    @Override
                    public Observable<String> call(final BaseBean baseBean) {
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                if (baseBean.getResult() == 0)
                                    subscriber.onNext("success");
                            }
                        });
                    }
                });
    }
}
