package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:订单的网络请求数据模型
 * author：remember
 * time：2016/5/28 16:34
 * email：1013773046@qq.com
 */
public class OrderUrlBean {
    /**
     * result : 0
     * msg : 请求成功
     * total : 3
     * rows : [{"ID":"1","Client":"1","Receiver":"R1","Address":"杭州下沙","Phone":"138383838438","Price":"40","Style":"已完成","Otime":"2016-05-29 17:11:14","foods":[{"FName":"微辣黄焖鸡米饭","Price":"288","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"},{"FName":"重辣黄焖鸡米饭","Price":"128","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"},{"FName":"不辣绿焖鸡米饭","Price":"20","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"}]},{"ID":"3","Client":"1","Receiver":"R1","Address":"杭州下沙","Phone":"138383838538","Price":"20","Style":"已完成","Otime":"2016-05-29 17:12:05","foods":[{"FName":"重辣黄焖鸡米饭","Price":"128","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"}]},{"ID":"4","Client":"1","Receiver":"R2","Address":"杭州下沙","Phone":"138383838438","Price":"30","Style":"已完成","Otime":"2016-05-29 17:12:06","foods":[{"FName":"微辣黄焖鸡米饭","Price":"288","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"},{"FName":"重辣黄焖鸡米饭","Price":"128","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"},{"FName":"重辣绿焖鸡米饭","Price":"100","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"}]}]
     */


    private int result;
    private String msg;
    private int total;
    /**
     * ID : 1
     * Client : 1
     * Receiver : R1
     * Address : 杭州下沙
     * Phone : 138383838438
     * Price : 40
     * Style : 已完成
     * Otime : 2016-05-29 17:11:14
     * foods : [{"FName":"微辣黄焖鸡米饭","Price":"288","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"},{"FName":"重辣黄焖鸡米饭","Price":"128","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"},{"FName":"不辣绿焖鸡米饭","Price":"20","Pic":"http://www.zousitanghulu.com/Pic/1.jpg"}]
     */

    private List<OrderBean> rows;

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

    public List<OrderBean> getRows() {
        return rows;
    }

    public void setRows(List<OrderBean> rows) {
        this.rows = rows;
    }

    public static class OrderBean {
        private String ID;
        private String Client;
        private String Receiver;
        private String Address;
        private String Phone;
        private String Price;
        private String Fee;
        private String Style;
        private String Otime;
        private String Deliver;
        private String RName;
        private String Pic;

        /**
         * FName : 微辣黄焖鸡米饭
         * Price : 288
         * Pic : http://www.zousitanghulu.com/Pic/1.jpg
         */

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getClient() {
            return Client;
        }

        public void setClient(String Client) {
            this.Client = Client;
        }

        public String getReceiver() {
            return Receiver;
        }

        public void setReceiver(String Receiver) {
            this.Receiver = Receiver;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getFee() {
            return Fee;
        }

        public void setFee(String fee) {
            Fee = fee;
        }

        public String getStyle() {
            return Style;
        }

        public void setStyle(String Style) {
            this.Style = Style;
        }

        public String getOtime() {
            return Otime;
        }

        public void setOtime(String Otime) {
            this.Otime = Otime;
        }


        public String getDeliver() {
            return Deliver;
        }

        public void setDeliver(String Deliver) {
            this.Deliver = Deliver;
        }

        public String getRName() {
            return RName;
        }

        public void setRName(String RName) {
            this.RName = RName;
        }

        public String getPic() {
            return Pic;
        }

        public void setPic(String Pic) {
            this.Pic = Pic;
        }
    }
}
