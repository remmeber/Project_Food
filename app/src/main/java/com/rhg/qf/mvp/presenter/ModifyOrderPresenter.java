package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.mvp.model.ModifyOrderModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:
 * author：remember
 * time：2016/7/10 23:29
 * email：1013773046@qq.com
 */
public class ModifyOrderPresenter {
    private BaseView modifyUserOrderView;
    private ModifyOrderModel modifyOrderModel;

    public ModifyOrderPresenter(BaseView modifyUserOrderView) {
        this.modifyUserOrderView = modifyUserOrderView;
        modifyOrderModel = new ModifyOrderModel();
    }

    public void modifyUserOrDeliverOrderState(String orderId, String style) {
        modifyOrderModel.modifyOrder(orderId, style).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("RHG", "onError:" + e.getMessage());

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("RHG", "修改结果：" + s);
                        modifyUserOrderView.showData(s);
                    }
                });
    }

}
