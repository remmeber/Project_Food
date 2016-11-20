package com.rhg.qf.bean;

import java.util.List;

/*
 *desc 跑腿员订单管理网络请求Bean
 *author rhg
 *time 2016/7/9 9:59
 *email 1013773046@qq.com
 */
public class DeliverOrderUrlBean {


    /**
     * result : 0
     * msg : 请求成功
     * total : 6
     * rows : [{"ID":"25","Style":"待接单","Name":"黄焖鸡米饭","Fee":"5"},{"ID":"26","Style":"待接单","Name":"黄焖鸡米饭","Fee":"5"}]
     */

    private int result;
    private String msg;
    private int total;
    /**
     * ID : 25
     * Style : 待接单
     * Name : 黄焖鸡米饭
     * Fee : 5
     */

    private List<DeliverOrderBean> rows;

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

    public List<DeliverOrderBean> getRows() {
        return rows;
    }

    public void setRows(List<DeliverOrderBean> rows) {
        this.rows = rows;
    }

    public static class DeliverOrderBean {
        private String ID;
        private String Style;
        private String Name;
        private String Price;
        private String Fee;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getStyle() {
            return Style;
        }

        public void setStyle(String Style) {
            this.Style = Style;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getFee() {
            return Fee;
        }

        public void setFee(String Fee) {
            this.Fee = Fee;
        }

        @Override
        public String toString() {
            return "DeliverOrderBean{" +
                    "ID='" + ID + '\'' +
                    ", Style='" + Style + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Price='" + Price + '\'' +
                    ", Fee='" + Fee + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DeliverOrderUrlBean{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", total=" + total +
                ", rows=" + rows.toString() +
                '}';
    }
}
