package com.rhg.qf.mvp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.rhg.qf.bean.NewOrderBackBean;
import com.rhg.qf.bean.NewOrderBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.utils.AccountUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * desc:添加新订单Model
 * author：remember
 * time：2016/7/16 0:13
 * email：1013773046@qq.com
 */
public class NewOrderModel {
    public Observable<NewOrderBackBean> createNewOrder(NewOrderBean newOrderBean) {
        List<Map<String, String>> mapList = new ArrayList<>();
        for (NewOrderBean.FoodBean foodBean : newOrderBean.getFood()) {
            Map<String, String> foodMap = new LinkedHashMap<>();
            foodMap.put("ID", foodBean.getID());
            foodMap.put("Num", foodBean.getNum());
            mapList.add(foodMap);
        }
        String food = new Gson().toJson(mapList);
        String X = AccountUtil.getInstance().getLongitude();
        String Y = AccountUtil.getInstance().getLatitude();
//        Log.i("RHG", "orderbean: " + newOrderBean + "X: " + X + "Y: " + Y);
        return QFoodApiMamager.getInstant().getQFoodApiService().createOrder(newOrderBean.getAddress(),
                newOrderBean.getClient(), newOrderBean.getReceiver(), newOrderBean.getPhone(),
                newOrderBean.getPrice(), X, Y, food)/*
                .create(new Observable.OnSubscribe<NewOrderBackBean>() {
                    @Override
                    public void call(Subscriber<? super NewOrderBackBean> subscriber) {
                        Log.i("RHG", newOrderBackBean.toString());
                        if (newOrderBackBean.getResult() == 0)
                            subscriber.onNext(newOrderBackBean.getMsg());
                        else subscriber.onNext("error");
                    }
                })*/;
    }
}
