package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.mvp.model.PerfectDeliverInfoModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:
 * author：remember
 * time：2016/7/15 0:32
 * email：1013773046@qq.com
 */
public class PerfectDeliverInfoPresenter {
    BaseView perfectDeliverInfoView;
    PerfectDeliverInfoModel perfectDeliverInfoModel;

    public PerfectDeliverInfoPresenter(BaseView perfectDeliverInfoView) {
        this.perfectDeliverInfoView = perfectDeliverInfoView;
        perfectDeliverInfoModel = new PerfectDeliverInfoModel();
    }

    public void perfectDeliverInfo(String name, String person_id, String phone,
                                   String area) {
        perfectDeliverInfoModel.perfectInfo(name, person_id, phone, area).observeOn(AndroidSchedulers.mainThread())
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
                        Log.i("RHG", "修改结果" + s);
                        perfectDeliverInfoView.showData(s);
                    }
                });
    }
}
