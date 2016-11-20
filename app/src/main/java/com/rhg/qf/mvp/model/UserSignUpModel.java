package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.BaseBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * desc:用户注册model
 * author：remember
 * time：2016/8/8 0:51
 * email：1013773046@qq.com
 */
public class UserSignUpModel {
    public Observable<String> userSignUp(String openid, String unionid, String headimageurl, String nickName) {
        return QFoodApiMamager.getInstant().getQFoodApiService().userSignUp(openid, unionid, headimageurl, nickName)
                .flatMap(new Func1<BaseBean, Observable<String>>() {
                    @Override
                    public Observable<String> call(final BaseBean baseBean) {
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                if (baseBean.getResult() == 1)
                                    subscriber.onNext("success");
                                else subscriber.onNext("fail");
                            }
                        });
                    }
                });
    }
}
