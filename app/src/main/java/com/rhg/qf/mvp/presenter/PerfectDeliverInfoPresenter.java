package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.mvp.model.PerfectDeliverInfoModel;
import com.rhg.qf.mvp.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * desc:
 * author：remember
 * time：2016/7/15 0:32
 * email：1013773046@qq.com
 */
public class PerfectDeliverInfoPresenter {
    BaseView perfectDeliverInfoView;
    PerfectDeliverInfoModel perfectDeliverInfoModel;

    public PerfectDeliverInfoPresenter(BaseView perfectDeliverInfoView) {
        this.perfectDeliverInfoView = perfectDeliverInfoView;
        perfectDeliverInfoModel = new PerfectDeliverInfoModel();
    }

    public void perfectDeliverInfo(String name, String person_id, String phone,
                                   String area) {
        perfectDeliverInfoModel.perfectInfo(name, person_id, phone, area).observeOn(AndroidSchedulers.mainThread())
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
                        Log.i("RHG", "修改结果" + s);
                        perfectDeliverInfoView.showData(s);
                    }
                });
    }
}
