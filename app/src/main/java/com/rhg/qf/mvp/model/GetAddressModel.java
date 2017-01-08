package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.BaseAddress;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.utils.AccountUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/*
 *desc
 *author rhg
 *time 2016/7/7 20:13
 *email 1013773046@qq.com
 */
public class GetAddressModel {

    public Observable<List<BaseAddress>> getAddress(String Table) {
        String userId = AccountUtil.getInstance().getUserID();
        return QFoodApiMamager.getInstant().getQFoodApiService().getAddress(Table, userId)
                .flatMap(new Func1<AddressUrlBean, Observable<List<BaseAddress>>>() {
                    @Override
                    public Observable<List<BaseAddress>> call(final AddressUrlBean addressUrlBean) {
                        final List<BaseAddress> addressList = new ArrayList<>();
                        Observable.from(addressUrlBean.getRows()).forEach(new Action1<AddressUrlBean.AddressBean>() {
                            @Override
                            public void call(AddressUrlBean.AddressBean addressBean) {
                                addressList.add(addressBean);
                            }
                        });
                        return Observable.just(addressList);
                    }
                });
    }
}
