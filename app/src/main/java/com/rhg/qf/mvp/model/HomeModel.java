package com.rhg.qf.mvp.model;

import android.util.Log;

import com.rhg.qf.bean.BannerTypeUrlBean;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.FavorableFoodUrlBean;
import com.rhg.qf.bean.HomeBean;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.mvp.api.QFoodApiService;
import com.rhg.qf.utils.AccountUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Func3;

/**
 * desc:mvp测试实现
 * author：remember
 * time：2016/5/28 17:00
 * email：1013773046@qq.com
 */
public class HomeModel {
    public Observable<HomeBean> getHomeData(String headrestaurants) {
        QFoodApiService qFoodApiService = QFoodApiMamager.getInstant().getQFoodApiService();
        String y = AccountUtil.getInstance().getLongitude();
        String x = AccountUtil.getInstance().getLatitude();
        Log.i("RHG", "MODEL");
        return Observable.zip(
                qFoodApiService.getBannerUrl(AppConstants.HEAD_ROW),
                qFoodApiService.getFavorableFood(AppConstants.HEAD_HOT),
                qFoodApiService.getHomeMerchants(headrestaurants, y, x),
                new Func3<BannerTypeUrlBean, FavorableFoodUrlBean,
                        MerchantUrlBean, HomeBean>() {
                    @Override
                    public HomeBean call(BannerTypeUrlBean bannerTypeUrlBean,
                                         FavorableFoodUrlBean favorableFoodUrlBean,
                                         MerchantUrlBean recommendListUrlBean
                    ) {
                        HomeBean _homeBean = new HomeBean();
                        if (bannerTypeUrlBean.getResult() == 0)
                            _homeBean.setBannerEntityList(bannerTypeUrlBean.getRows());
                        else Log.i("RHG", "getBannerUrl WRONG ");
                        _homeBean.setFavorableFoodEntityList(favorableFoodUrlBean.getRows());

                       /* List<MerchantUrlBean.MerchantBean> _list = recommendListUrlBean.getRows();
                        final List<CommonListModel<MerchantUrlBean.MerchantBean>> commonListModels = new ArrayList<>();
                        for (MerchantUrlBean.MerchantBean _bean :
                                _list) {
                            CommonListModel<MerchantUrlBean.MerchantBean> bean = new CommonListModel<>();
                            bean.setRecommendShopBeanEntity(Collections.singletonList(_bean));
                            commonListModels.add(bean);
                        }*/
                        _homeBean.setRecommendShopBeanEntityList(recommendListUrlBean.getRows());
                        return _homeBean;
                    }
                });
    }
}