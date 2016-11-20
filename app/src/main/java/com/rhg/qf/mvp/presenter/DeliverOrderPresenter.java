package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.DeliverOrderUrlBean;
import com.rhg.qf.mvp.model.DeliverOrderModel;
import com.rhg.qf.mvp.view.BaseView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
                .subscribe(new Observer<List<DeliverOrderUrlBean.DeliverOrderBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeen) {
                        deliverOrderView.showData(deliverOrderBeen);
                    }
                });

    }
}
