package com.rhg.qf.bean;

import java.util.List;

/*
 *desc
 *author rhg
 *time 2016/7/9 16:30
 *email 1013773046@qq.com
 */
public class HeadMerchantUrlBean {


    /**
     * result : 0
     * msg : 请求成功
     * total : 1
     * rows : [{"ID":"1","Name":"黄焖鸡米饭","Pic":"http://zousitanghulu.com/Pic/FoodPic/toppic.jpg","Delivery":"100","Fee":"5","Distance":"100","Style":"中餐"}]
     */

    private int result;
    private String msg;
    private int total;
    /**
     * ID : 1
     * Name : 黄焖鸡米饭
     * Pic : http://zousitanghulu.com/Pic/FoodPic/toppic.jpg
     * Delivery : 100
     * Fee : 5
     * Distance : 100
     * Style : 中餐
     */

    private List<MerchantUrlBean.MerchantBean> rows;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MerchantUrlBean.MerchantBean> getRows() {
        return rows;
    }

    public void setRows(List<MerchantUrlBean.MerchantBean> rows) {
        this.rows = rows;
    }


}
