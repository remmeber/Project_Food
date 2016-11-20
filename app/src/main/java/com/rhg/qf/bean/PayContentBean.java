package com.rhg.qf.bean;

/**
 * desc:用于支付详情的显示
 * author：remember
 * time：2016/5/28 16:34
 * email：1013773046@qq.com
 */
public class PayContentBean {
    String goodsName;
    String goodsDescription;
    String payMoney;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    @Override
    public String toString() {
        return "goodsName: " + goodsName + " goodsDescription: " + " payMoney: ";
    }
}
