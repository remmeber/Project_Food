package com.rhg.qf.mvp.model;

import android.util.Log;

import com.rhg.qf.bean.BannerTypeUrlBean;
import com.rhg.qf.bean.FavorableFoodUrlBean;
import com.rhg.qf.bean.HomeBean;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.bean.TextTypeBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.mvp.api.QFoodApiService;
import com.rhg.qf.utils.AccountUtil;

import rx.Observable;
import rx.functions.Func4;

/**
 * desc:mvp测试实现
 * author：remember
 * time：2016/5/28 17:00
 * email：1013773046@qq.com
 */
public class HomeModel {

    public Observable<HomeBean> getHomeData(String headrestaurants) {
        QFoodApiService qFoodApiService = QFoodApiMamager.getInstant().getQFoodApiService();
        String x = AccountUtil.getInstance().getLongitude();
        String y = AccountUtil.getInstance().getLatitude();
        return Observable.zip(
                qFoodApiService.getBannerUrl(AppConstants.HEAD_ROW),
                qFoodApiService.getFavorableFood(AppConstants.HEAD_HOT),
                qFoodApiService.getHomeMerchants(headrestaurants, x, y),
                qFoodApiService.getMessage(),
                new Func4<BannerTypeUrlBean, FavorableFoodUrlBean,
                        MerchantUrlBean, TextTypeBean, HomeBean>() {
                    @Override
                    public HomeBean call(BannerTypeUrlBean bannerTypeUrlBean,
                                         FavorableFoodUrlBean favorableFoodUrlBean,
                                         MerchantUrlBean recommendListUrlBean,
                                         TextTypeBean textTypeBean
                    ) {
                        HomeBean _homeBean = new HomeBean();
                        if (bannerTypeUrlBean.getResult() == 0)
                            _homeBean.setBannerEntityList(bannerTypeUrlBean.getRows());
                        else Log.i("RHG","getBannerUrl WRONG ");
                        _homeBean.setFavorableFoodEntityList(favorableFoodUrlBean.getRows());
                        _homeBean.setRecommendShopBeanEntityList(recommendListUrlBean.getRows());
                        _homeBean.setTextTypeBean(textTypeBean);
                        return _homeBean;
                    }
                });
    }
}