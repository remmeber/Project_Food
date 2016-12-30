package com.rhg.qf.mvp.presenter;

import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.BaseAddress;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.mvp.model.GetAddressModel;
import com.rhg.qf.mvp.view.BaseView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
                .subscribe(new Observer<List<BaseAddress>>() {
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
