package com.rhg.qf.bean;

/*
 *desc
 *author rhg
 *time 2016/10/19 11:03
 *email 1013773046@qq.com
 */

public class NewOrderBackBean extends BaseBean {
    private String fee;
    private String price;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString()+"NewOrderBackBean{" +
                "fee='" + fee + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
