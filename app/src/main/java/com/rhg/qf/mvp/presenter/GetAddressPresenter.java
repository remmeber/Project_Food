package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.BaseAddress;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.mvp.model.GetAddressModel;
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
 * desc:
 * author：remember
 * time：2016/6/29 16:01
 * email：1013773046@qq.com
 */
public class GetAddressPresenter {
    BaseView baseView;
    GetAddressModel getAddressModel;

    public GetAddressPresenter(BaseView baseView) {
        this.baseView = baseView;
        getAddressModel = new GetAddressModel();
    }

    public void getAddress(String Table) {
        getAddressModel.getAddress(Table).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Func1<Throwable, List<BaseAddress>>() {
                    @Override
                    public List<BaseAddress> call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribe(new Subscriber<List<BaseAddress>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<BaseAddress> addressBean) {
                        CommonListModel<BaseAddress> addressModel = new CommonListModel<>();
                        addressModel.setRecommendShopBeanEntity(addressBean);
                        baseView.showData(addressModel);
                    }
                });
    }
}
