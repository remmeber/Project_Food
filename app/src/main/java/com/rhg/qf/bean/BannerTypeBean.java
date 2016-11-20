package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:图片轮播本地数据模型 ，需要将 {@link BannerTypeUrlBean}转换为该类使用
 * author：remember
 * time：2016/5/28 16:22
 * email：1013773046@qq.com
 */
public class BannerTypeBean {
    private List<BannerTypeUrlBean.BannerEntity> bannerEntityList;

    public List<BannerTypeUrlBean.BannerEntity> getBannerEntityList() {
        return bannerEntityList;
    }

    public void setBannerEntityList(List<BannerTypeUrlBean.BannerEntity> bannerEntityList) {
        this.bannerEntityList = bannerEntityList;
    }
}
