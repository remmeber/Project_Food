package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:店铺模型  保存该店铺所有的商品信息
 * author：remember
 * time：2016/5/28 16:33
 * email：1013773046@qq.com
 */
public class MerchantUrlBean {

    /**
     * total : 6
     * rows : [{"ID":"1","Name":"米其林餐厅1","Pic":"http://www.zousitanghulu.com/json/pic/r1.jpg","Delivery":"满20起送","Fee":"配送费1元","Distance":"距离100米","Style":"西餐"},{"ID":"2","Name":"米其林餐厅2","Pic":"http://www.zousitanghulu.com/json/pic/r1.jpg","Delivery":"满21起送","Fee":"配送费2元","Distance":"距离200米","Style":"中餐"},{"ID":"3","Name":"米其林餐厅3","Pic":"http://www.zousitanghulu.com/json/pic/r1.jpg","Delivery":"满120起送","Fee":"配送费10元","Distance":"距离1千米","Style":"西餐"},{"ID":"4","Name":"米其林餐厅4","Pic":"http://www.zousitanghulu.com/json/pic/r1.jpg","Delivery":"满20起送","Fee":"配送费1元","Distance":"距离100米","Style":"西餐"},{"ID":"5","Name":"米其林餐厅5","Pic":"http://www.zousitanghulu.com/json/pic/r1.jpg","Delivery":"满20起送","Fee":"配送费1元","Distance":"距离100米","Style":"西餐"},{"ID":"6","Name":"米其林餐厅6","Pic":"http://www.zousitanghulu.com/json/pic/r1.jpg","Delivery":"满20起送","Fee":"配送费1元","Distance":"距离100米","Style":"西餐"}]
     */

    private String total;
    /**
     * ID : 1
     * Name : 米其林餐厅1
     * Pic : http://www.zousitanghulu.com/json/pic/r1.jpg
     * Delivery : 满20起送
     * Fee : 配送费1元
     * Distance : 距离100米
     * Style : 西餐
     */

    private List<MerchantBean> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<MerchantBean> getRows() {
        return rows;
    }

    public void setRows(List<MerchantBean> rows) {
        this.rows = rows;
    }

    public static class MerchantBean {

        private String ID;
        private String Name;
        private String Pic;
        private String Delivery;
        private String Fee;
        private String Distance;
        private String Style;
        private String Reason;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPic() {
            return Pic;
        }

        public void setPic(String Pic) {
            this.Pic = Pic;
        }

        public String getDelivery() {
            return Delivery;
        }

        public void setDelivery(String Delivery) {
            this.Delivery = Delivery;
        }

        public String getFee() {
            return Fee;
        }

        public void setFee(String Fee) {
            this.Fee = Fee;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String Distance) {
            this.Distance = Distance;
        }

        public String getStyle() {
            return Style;
        }

        public void setStyle(String Style) {
            this.Style = Style;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String reason) {
            this.Reason = reason;
        }

        @Override
        public String toString() {
            return "MerchantBean{" +
                    "ID='" + ID + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Pic='" + Pic + '\'' +
                    ", Delivery='" + Delivery + '\'' +
                    ", Fee='" + Fee + '\'' +
                    ", Distance='" + Distance + '\'' +
                    ", Style='" + Style + '\'' +
                    ", reason='" + Reason + '\'' +
                    '}';
        }
    }
}
