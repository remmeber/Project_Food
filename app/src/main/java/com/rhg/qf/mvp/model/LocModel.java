package com.rhg.qf.mvp.model;

import com.rhg.qf.locationservice.LocationService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:mvp中获取定位的实现
 * author：remember
 * time：2016/5/28 16:59
 * email：1013773046@qq.com
 */
public class LocModel {
    public void getLocation(LocationService locationService) {
        Observable.just(locationService).flatMap(new Func1<LocationService, Observable<String>>() {
            @Override
            public Observable<String> call(final LocationService locationService) {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                /*定位操作*/
                        locationService.start();
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
