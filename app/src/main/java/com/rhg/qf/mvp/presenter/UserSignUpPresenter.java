package com.rhg.qf.mvp.presenter;

import com.rhg.qf.mvp.model.UserSignUpModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
