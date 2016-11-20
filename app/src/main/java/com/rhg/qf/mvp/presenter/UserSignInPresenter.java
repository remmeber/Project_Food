package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.bean.SignInBackBean;
import com.rhg.qf.mvp.model.UserSignInModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
