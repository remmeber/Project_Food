package com.rhg.qf.bean;

/*
 *desc
 *author rhg
 *time 2016/10/19 11:03
 *email 1013773046@qq.com
 */

import java.util.List;

public class NewOrderBackModel extends BaseBean {

    List<NewOrderBackBean> rows;

    public List<NewOrderBackBean> getRows() {
        return rows;
    }

    public void setRows(List<NewOrderBackBean> rows) {
        this.rows = rows;
    }

    public static  class NewOrderBackBean {
        private String RId;
        private String fee;
        private String price;

        public String getRId() {
            return RId;
        }

        public void setRId(String RId) {
            this.RId = RId;
        }

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
            return "NewOrderBackModel{" +
                    "RId='" + RId + '\'' +
                    ", fee='" + fee + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
    }
}
