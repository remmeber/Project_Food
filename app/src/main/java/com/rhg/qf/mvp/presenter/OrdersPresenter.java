package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.OrderUrlBean;
import com.rhg.qf.mvp.model.OrdersModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                .onErrorReturn(new Func1<Throwable, List<OrderUrlBean.OrderBean>>() {
                    @Override
                    public List<OrderUrlBean.OrderBean> call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
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
                        CommonListModel<OrderUrlBean.OrderBean> orderBeanModel = new CommonListModel();
                        orderBeanModel.setRecommendShopBeanEntity(orderBeanList);
                        testView.showData(orderBeanModel);
                    }
                });
    }
}
