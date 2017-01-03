package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.DeliverInfoBean;
import com.rhg.qf.mvp.model.GetDeliverInfoModel;
import com.rhg.qf.mvp.model.PerfectDeliverInfoModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:
 * author：remember
 * time：2016/7/15 0:32
 * email：1013773046@qq.com
 */
public class GetDeliverInfoPresenter {
    BaseView getDeliverInfoView;
    GetDeliverInfoModel getDeliverInfoModel;

    public GetDeliverInfoPresenter(BaseView perfectDeliverInfoView) {
        this.getDeliverInfoView = perfectDeliverInfoView;
        getDeliverInfoModel = new GetDeliverInfoModel();
    }

    public void getDeliverInfo(String deliverTable) {
        getDeliverInfoModel.getDeliverInfo(deliverTable).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Func1<Throwable, DeliverInfoBean.InfoBean>() {
                    @Override
                    public DeliverInfoBean.InfoBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribe(new Subscriber<DeliverInfoBean.InfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DeliverInfoBean.InfoBean infoBean) {
                        getDeliverInfoView.showData(infoBean);
                    }
                });
    }
}
