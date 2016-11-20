package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.OrderUrlBean;
import com.rhg.qf.mvp.model.OrdersModel;
import com.rhg.qf.mvp.view.BaseView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:mvp presenter 测试实现类
 * author：remember
 * time：2016/5/28 17:02
 * email：1013773046@qq.com
 */
public class OrdersPresenter {
    BaseView testView;
    OrdersModel getOrdersModel;

    public OrdersPresenter(BaseView baseView) {
        testView = baseView;
        getOrdersModel = new OrdersModel();
    }

    public void getOrders(String table, String userId, int style) {
        getOrdersModel.getOrders(table, userId, style).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<OrderUrlBean.OrderBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrderUrlBean.OrderBean> orderBeanList) {
                        testView.showData(orderBeanList);
                    }
                });
    }
}
