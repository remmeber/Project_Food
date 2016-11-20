package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.utils.AccountUtil;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/*
 *desc
 *author rhg
 *time 2016/7/7 20:13
 *email 1013773046@qq.com
 */
public class GetAddressModel {

    public Observable<List<AddressUrlBean.AddressBean>> getAddress(String Table) {
        String userId = AccountUtil.getInstance().getUserID();
//        Log.i("RHG", "GET ADDRESS Presenter:" + userId);
        return QFoodApiMamager.getInstant().getQFoodApiService().getAddress(Table, userId)
                .flatMap(new Func1<AddressUrlBean, Observable<List<AddressUrlBean.AddressBean>>>() {
                    @Override
                    public Observable<List<AddressUrlBean.AddressBean>> call(final AddressUrlBean addressUrlBean) {
                        return Observable.just(addressUrlBean.getRows());
                    }
                });
    }
}
