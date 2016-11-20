package com.rhg.qf.utils;

import com.bigkoo.convenientbanner.ConvenientBanner;

/**
 * desc:轮播工具
 * author：remember
 * time：2016/6/5 13:51
 * email：1013773046@qq.com
 */
public class BannerController {
    ConvenientBanner convenientBanner;

    public BannerController() {
    }

    /*private static BannerController ourInstance = new BannerController();

    public static BannerController getInstance() {
        return ourInstance;
    }*/

    public void setConvenientBanner(ConvenientBanner convenientBanner) {
        this.convenientBanner = convenientBanner;
    }

    public void startBanner(long time) {
        if (convenientBanner != null && !convenientBanner.isTurning()) {
            convenientBanner.startTurning(time);
        }
    }

    public void stopBanner() {
        if (convenientBanner != null && convenientBanner.isTurning()) {
            convenientBanner.stopTurning();
        }
    }

}
