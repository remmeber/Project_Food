package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:整个主页的数据模型，网络请求后，需要将所有的数据都整合到该类中
 * author：remember
 * time：2016/5/28 16:33
 * email：1013773046@qq.com
 */
public class HomeBean {
    List<BannerTypeUrlBean.BannerEntity> bannerEntityList;
    TextTypeBean textTypeBean;
    List<FavorableFoodUrlBean.FavorableFoodEntity> favorableFoodEntityList;
    List<MerchantUrlBean.MerchantBean> recommendShopBeanEntityList;

    public List<BannerTypeUrlBean.BannerEntity> getBannerEntityList() {
        return bannerEntityList;
    }

    public void setBannerEntityList(List<BannerTypeUrlBean.BannerEntity> bannerEntityList) {
        this.bannerEntityList = bannerEntityList;
    }

    public TextTypeBean getTextTypeBean() {
        return textTypeBean;
    }

    public void setTextTypeBean(TextTypeBean textTypeBean) {
        this.textTypeBean = textTypeBean;
    }

    public List<FavorableFoodUrlBean.FavorableFoodEntity> getFavorableFoodEntityList() {
        return favorableFoodEntityList;
    }

    public void setFavorableFoodEntityList(List<FavorableFoodUrlBean.FavorableFoodEntity> favorableFoodEntityList) {
        this.favorableFoodEntityList = favorableFoodEntityList;
    }

    public List<MerchantUrlBean.MerchantBean> getRecommendShopBeanEntityList() {
        return recommendShopBeanEntityList;
    }

    public void setRecommendShopBeanEntityList(List<MerchantUrlBean.MerchantBean> recommendShopBeanEntityList) {
        this.recommendShopBeanEntityList = recommendShopBeanEntityList;
    }
}
