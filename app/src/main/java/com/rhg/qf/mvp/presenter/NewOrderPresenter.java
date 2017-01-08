package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.bean.NewOrderBackBean;
import com.rhg.qf.bean.NewOrderBean;
import com.rhg.qf.mvp.model.NewOrderModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:添加新订单Presenter
 * author：remember
 * time：2016/7/16 0:15
 * email：1013773046@qq.com
 */
public class NewOrderPresenter {
    BaseView createNewOrderView;
    NewOrderModel createNewOrderModel;

    public NewOrderPresenter(BaseView createNewOrderView) {
        this.createNewOrderView = createNewOrderView;
        createNewOrderModel = new NewOrderModel();
    }

    public void createNewOrder(NewOrderBean newOrderBean) {
        createNewOrderModel.createNewOrder(newOrderBean).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Func1<Throwable, NewOrderBackBean>() {
                    @Override
                    public NewOrderBackBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribe(new Observer<NewOrderBackBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("RHG", "error:" + e.getMessage() + "\ncause:" + e.getCause());
                    }

                    @Override
                    public void onNext(NewOrderBackBean s) {

                        if (s.getResult() == 0) {
                            createNewOrderView.showData(s);
                        } else
                            createNewOrderView.showData("new_order_error");
                    }
                });
    }
}
