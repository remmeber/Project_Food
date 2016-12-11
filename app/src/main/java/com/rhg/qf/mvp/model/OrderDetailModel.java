package com.rhg.qf.mvp.model;


import com.rhg.qf.bean.OrderDetailUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/*
 *desc
 *author rhg
 *time 2016/7/10 17:20
 *email 1013773046@qq.com
 */
public class OrderDetailModel {
    public Observable<OrderDetailUrlBean.OrderDetailBean> getOrderDetail(final String orderDetail, String orderId) {
        return QFoodApiMamager.getInstant().getQFoodApiService().getOrderDetail(orderDetail, orderId)
                .flatMap(new Func1<OrderDetailUrlBean, Observable<OrderDetailUrlBean.OrderDetailBean>>() {
                    @Override
                    public Observable<OrderDetailUrlBean.OrderDetailBean> call(final OrderDetailUrlBean orderDetailUrlBean) {
                        List<OrderDetailUrlBean.OrderDetailBean.FoodsBean> foodsBean = orderDetailUrlBean.getRows().get(0).getFoods();
                        String last = "";
                        List<Integer> index = new ArrayList<>();
                        int size = foodsBean.size();
                        Collections.sort(foodsBean, new Comparator<OrderDetailUrlBean.OrderDetailBean.FoodsBean>() {
                            @Override
                            public int compare(OrderDetailUrlBean.OrderDetailBean.FoodsBean o1, OrderDetailUrlBean.OrderDetailBean.FoodsBean o2) {
                                return o1.getRName().compareTo(o2.getRName());
                            }
                        });
                        for (int i = 0; i < size; i++) {
                            String newName = foodsBean.get(i).getRName();
                            if (!last.equals(newName)) {
                                index.add(i + index.size());
                                last = newName;
                            }
                        }
                        for (int i : index) {
                            OrderDetailUrlBean.OrderDetailBean.FoodsBean newFood = new OrderDetailUrlBean.OrderDetailBean.FoodsBean();
                            newFood.setRName(foodsBean.get(i).getRName());
                            foodsBean.add(i, newFood);
                        }
                        orderDetailUrlBean.getRows().get(0).setIndex(index);/*目的是设置一个订单中不同商家的位置*/
                        return Observable.just(orderDetailUrlBean.getRows().get(0));
                    }
                });
    }
}
