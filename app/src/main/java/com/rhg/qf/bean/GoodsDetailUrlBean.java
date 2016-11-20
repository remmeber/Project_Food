package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:
 * author：remember
 * time：2016/7/7 23:50
 * email：1013773046@qq.com
 */
public class GoodsDetailUrlBean {
    /**
     * result : 0
     * msg : 请求成功
     * total : 1
     * rows : [{"Name":"微辣绿焖鸡米饭","Price":"200","MonthlySales":"0","Style":"有货","Message":"微辣的绿焖鸡米饭","Picnum":2,"Picsrc":["http://www.zousitanghulu.com/Pic/1.jpg","http://zousitanghulu.com/Pic/FoodPic/food3.jpg"]}]
     */

    private int result;
    private String msg;
    private int total;
    /**
     * Name : 微辣绿焖鸡米饭
     * Price : 200
     * MonthlySales : 0
     * Style : 有货
     * Message : 微辣的绿焖鸡米饭
     * Picnum : 2
     * Picsrc : ["http://www.zousitanghulu.com/Pic/1.jpg","http://zousitanghulu.com/Pic/FoodPic/food3.jpg"]
     */

    private List<GoodsDetailBean> rows;

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

    public List<GoodsDetailBean> getRows() {
        return rows;
    }

    public void setRows(List<GoodsDetailBean> rows) {
        this.rows = rows;
    }

    public static class GoodsDetailBean {
        private String Name;
        private String Price;
        private String MonthlySales;
        private String Style;
        private String Message;
        private int Picnum;
        private List<String> Picsrc;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getMonthlySales() {
            return MonthlySales;
        }

        public void setMonthlySales(String MonthlySales) {
            this.MonthlySales = MonthlySales;
        }

        public String getStyle() {
            return Style;
        }

        public void setStyle(String Style) {
            this.Style = Style;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public int getPicnum() {
            return Picnum;
        }

        public void setPicnum(int Picnum) {
            this.Picnum = Picnum;
        }

        public List<String> getPicsrc() {
            return Picsrc;
        }

        public void setPicsrc(List<String> Picsrc) {
            this.Picsrc = Picsrc;
        }
    }

}
