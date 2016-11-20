package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:
 * author：remember
 * time：2016/7/21 22:41
 * email：1013773046@qq.com
 */
public class ShopDetailLocalModel {

    private List<String> Varietys;

    private List<ShopDetailUrlBean.ShopDetailBean> shopDetailBean;

    public List<String> getVarietys() {
        return Varietys;
    }

    public void setVarietys(List<String> varietys) {
        Varietys = varietys;
    }

    public List<ShopDetailUrlBean.ShopDetailBean> getShopDetailBean() {
        return shopDetailBean;
    }

    public void setShopDetailBean(List<ShopDetailUrlBean.ShopDetailBean> shopDetailBean) {
        this.shopDetailBean = shopDetailBean;
    }
}
