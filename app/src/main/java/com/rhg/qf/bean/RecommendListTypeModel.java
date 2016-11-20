package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:主页店铺推荐列表模型
 * author：remember
 * time：2016/5/28 16:38
 * email：1013773046@qq.com
 */
public class RecommendListTypeModel {
    List<MerchantUrlBean.MerchantBean> recommendShopBeanEntity;

    public List<MerchantUrlBean.MerchantBean> getRecommendShopBeanEntity() {
        return recommendShopBeanEntity;
    }

    public void setRecommendShopBeanEntity(
            List<MerchantUrlBean.MerchantBean> recommendShopBeanEntity) {
        this.recommendShopBeanEntity = recommendShopBeanEntity;

    }
}
