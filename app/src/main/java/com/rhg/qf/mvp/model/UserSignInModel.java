package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.SignInBackBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import rx.Observable;
import rx.functions.Func1;

/**
 * desc:用户注册model
 * author：remember
 * time：2016/8/8 0:51
 * email：1013773046@qq.com
 */
public class UserSignInModel {
    public Observable<SignInBackBean.UserInfoBean> userSignIn(String client, String userName,
                                                              String pwd) {
        return QFoodApiMamager.getInstant().getQFoodApiService().userSignIn(client, userName, pwd)
                .flatMap(new Func1<SignInBackBean, Observable<SignInBackBean.UserInfoBean>>() {
                    @Override
                    public Observable<SignInBackBean.UserInfoBean> call(final SignInBackBean signInBackBean) {
                        if (signInBackBean.getResult() == 2)
                            return Observable.just(null);
                        return Observable.just(signInBackBean.getRows().get(0));
                    }
                });
    }
}
