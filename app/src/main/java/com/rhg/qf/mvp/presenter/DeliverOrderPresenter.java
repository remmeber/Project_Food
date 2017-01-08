package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.DeliverOrderUrlBean;
import com.rhg.qf.mvp.model.DeliverOrderModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:跑腿员订单presenter
 * author：remember
 * time：2016/7/10 11:15
 * email：1013773046@qq.com
 */
public class DeliverOrderPresenter {
    BaseView deliverOrderView;
    DeliverOrderModel deliverOrderModel;

    public DeliverOrderPresenter(BaseView deliverOrderView) {
        this.deliverOrderView = deliverOrderView;
        deliverOrderModel = new DeliverOrderModel();
    }

    public void getDeliverOrder(String deliverOrder, String deliverId) {
        deliverOrderModel.getDeliverOrder(deliverOrder, deliverId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Func1<Throwable, List<DeliverOrderUrlBean.DeliverOrderBean>>() {
                    @Override
                    public List<DeliverOrderUrlBean.DeliverOrderBean> call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribe(new Subscriber<List<DeliverOrderUrlBean.DeliverOrderBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeen) {
                        CommonListModel<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderModel = new CommonListModel<>();
                        deliverOrderModel.setRecommendShopBeanEntity(deliverOrderBeen);
                        deliverOrderView.showData(deliverOrderModel);

                    }
                });

    }
}
