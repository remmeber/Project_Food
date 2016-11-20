package com.rhg.qf.mvp.presenter;

import com.rhg.qf.mvp.model.AddressModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:添加新地址presenter
 * author：remember
 * time：2016/7/11 23:34
 * email：1013773046@qq.com
 */
public class AddOrUpdateAddressPresenter {
    BaseView addNewAddressView;
    AddressModel addAddressModel;

    public AddOrUpdateAddressPresenter(BaseView addNewAddressView) {
        this.addNewAddressView = addNewAddressView;
        addAddressModel = new AddressModel();
    }

    public void addOrUpdateAddress(String addressId, String user, String phone, String address, String detail,
                                   String opt) {
        addAddressModel.addOrUpdateAddress(addressId, user, phone, address, detail, opt)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        addNewAddressView.showData(s);
                    }
                });
    }
}
