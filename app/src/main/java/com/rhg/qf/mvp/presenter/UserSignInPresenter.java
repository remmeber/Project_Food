package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.bean.SignInBackBean;
import com.rhg.qf.mvp.model.UserSignInModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:用户登录Presenter
 * author：remember
 * time：2016/8/8 0:55
 * email：1013773046@qq.com
 */
public class UserSignInPresenter {
    BaseView userSignInView;
    UserSignInModel userSignInModel;

    public UserSignInPresenter(BaseView userSignUpView) {
        this.userSignInView = userSignUpView;
        userSignInModel = new UserSignInModel();
    }

    public void userSignIn(String client, String userName, String pwd) {
        userSignInModel.userSignIn(client, userName, pwd).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, SignInBackBean.UserInfoBean>() {
                    @Override
                    public SignInBackBean.UserInfoBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SignInBackBean.UserInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("RHG", e.getMessage());

                    }

                    @Override
                    public void onNext(SignInBackBean.UserInfoBean userInfoBean) {
                        userSignInView.showData(userInfoBean);
                    }
                });
    }
}
