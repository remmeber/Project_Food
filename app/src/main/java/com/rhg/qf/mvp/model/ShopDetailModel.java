package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.ShopDetailLocalModel;
import com.rhg.qf.bean.ShopDetailUrlBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;

import java.util.Collections;
import java.util.Comparator;

import rx.Observable;
import rx.functions.Func1;

/**
 * desc:
 * author：remember
 * time：2016/5/28 17:00
 * email：1013773046@qq.com
 */
public class ShopDetailModel {
    public Observable<ShopDetailLocalModel> getShopDetail(String table, String merchantId) {
        return QFoodApiMamager.getInstant().getQFoodApiService().
                getMerchantFoods(table, Integer.valueOf(merchantId))
                .flatMap(new Func1<ShopDetailUrlBean, Observable<ShopDetailLocalModel>>() {

                    @Override
                    public Observable<ShopDetailLocalModel> call(final ShopDetailUrlBean
                                                                         shopDetailUrlBean) {

                        Collections.sort(shopDetailUrlBean.getRows(), new Comparator<ShopDetailUrlBean.ShopDetailBean>() {
                            @Override
                            public int compare(ShopDetailUrlBean.ShopDetailBean o1, ShopDetailUrlBean.ShopDetailBean o2) {
                                return o1.getVariety().compareTo(o2.getVariety());
                            }
                        });
                        Collections.sort(shopDetailUrlBean.getVarietys(), new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                return o1.compareTo(o2);
                            }
                        });
                        ShopDetailLocalModel shopDetailLocalModel = new ShopDetailLocalModel();
                        shopDetailLocalModel.setVarietys(shopDetailUrlBean.getVarietys());
                        shopDetailLocalModel.setShopDetailBean(shopDetailUrlBean.getRows());
                        return Observable/*create(new Observable.OnSubscribe<ShopDetailLocalModel>() {
                            @Override
                            public void call(Subscriber<? super ShopDetailLocalModel> subscriber) {
                                List<ShopDetailUrlBean.ShopDetailBean> shopDetailBeanList = new ArrayList<>();
                                int title_count = shopDetailUrlBean.getVarietys().size();
                                int item_count = shopDetailUrlBean.getTotal();
                                for (int t = 0; t < title_count; t++) {
                                    List<ShopDetailUrlBean.ShopDetailBean> _localBean = new ArrayList<>();
                                    for (int i = 0; i < item_count; i++) {
                                        if (shopDetailUrlBean.getVarietys().get(t).equals(shopDetailUrlBean.getRows().get(i).getVariety()))
                                            _localBean.add(shopDetailUrlBean.getRows().get(i));
                                    }
                                    shopDetailBeanList.addAll(_localBean);
                                }
                                List<String> title = new ArrayList<>();
                                title.addAll(shopDetailUrlBean.getVarietys());
                                ShopDetailLocalModel shopDetailLocalModel = new ShopDetailLocalModel();
                                shopDetailLocalModel.setVarietys(title);
                                shopDetailLocalModel.setShopDetailBean(shopDetailBeanList);
                                subscriber.onNext(shopDetailLocalModel);
                            }
                        });*/.just(shopDetailLocalModel);
                    }
                });
    }
}

