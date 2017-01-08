package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.bean.GoodsDetailUrlBean;
import com.rhg.qf.mvp.base.RxPresenter;
import com.rhg.qf.mvp.model.GoodsDetailModel;
import com.rhg.qf.mvp.presenter.contact.GoodsDetailContact;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:mvp中获取商品详情的presenter实现
 * author：remember
 * time：2016/5/28 17:01
 * email：1013773046@qq.com
 */
public class GoodsDetailPresenter extends RxPresenter<GoodsDetailContact.View<GoodsDetailUrlBean.GoodsDetailBean>> {

    GoodsDetailModel goodsDetailModel;

    /*public GoodsDetailPresenter(BaseView baseView) {
        this.baseView = baseView;
        goodsDetailModel = new GoodsDetailModel();
    }*/

    public GoodsDetailPresenter(/*GoodsDetailContact.View<GoodsDetailUrlBean.GoodsDetailBean> view*/) {
        goodsDetailModel = new GoodsDetailModel();
    }

    public void getGoodsInfo(String foodmessage, String foodId) {
        addSubscription(goodsDetailModel.getGoodsDetail(foodmessage, foodId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Func1<Throwable, GoodsDetailUrlBean.GoodsDetailBean>() {
                    @Override
                    public GoodsDetailUrlBean.GoodsDetailBean call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribe(new Subscriber<GoodsDetailUrlBean.GoodsDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("RHG", "error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(GoodsDetailUrlBean.GoodsDetailBean goodsDetailBean) {
//                        baseView.showData(goodsDetailBean);
                        view.showContent(goodsDetailBean);
                    }
                }));

    }
}
