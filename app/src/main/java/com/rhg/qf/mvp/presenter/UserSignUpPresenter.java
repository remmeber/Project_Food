package com.rhg.qf.mvp.presenter;

import com.rhg.qf.mvp.model.UserSignUpModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:用户注册Presenter
 * author：remember
 * time：2016/8/8 0:55
 * email：1013773046@qq.com
 */
public class UserSignUpPresenter {
    BaseView userSignUpView;
    UserSignUpModel userSignUpModel;

    public UserSignUpPresenter(BaseView userSignUpView) {
        this.userSignUpView = userSignUpView;
        userSignUpModel = new UserSignUpModel();
    }

    public void userSignUp(String openid, String unionid, String headimageurl, String nickName) {
        userSignUpModel.userSignUp(openid, unionid, headimageurl, nickName).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        userSignUpView.showData(s);
                    }
                });
    }
}
