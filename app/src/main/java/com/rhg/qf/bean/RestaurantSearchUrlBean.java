package com.rhg.qf.bean;

import java.util.List;

/*
 *desc
 *author rhg
 *time 2016/7/7 17:03
 *email 1013773046@qq.com
 */
public class RestaurantSearchUrlBean {

    /**
     * total : 2
     * rows : [{"ID":"1","Name":"黄焖鸡米饭","Pic":"http://www.zousitanghulu.com/json/pic/r1.jpg","Delivery":"100","Fee":"5","Distance":"杭州下沙","Style":"中餐"},{"ID":"2","Name":"绿焖鸡米饭","Pic":"http://www.zousitanghulu.com/json/pic/r1.jpg","Delivery":"200","Fee":"4","Distance":"杭州下沙","Style":"西餐"}]
     */

    private int total;
    /**
     * ID : 1
     * Name : 黄焖鸡米饭
     * Pic : http://www.zousitanghulu.com/json/pic/r1.jpg
     * Delivery : 100
     * Fee : 5
     * Distance : 杭州下沙
     * Style : 中餐
     */

    private List<RestaurantSearchBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RestaurantSearchBean> getRows() {
        return rows;
    }

    public void setRows(List<RestaurantSearchBean> rows) {
        this.rows = rows;
    }

    public static class RestaurantSearchBean {
        private String ID;
        private String Name;
        private String Pic;
        private String Delivery;
        private String Fee;
        private String Distance;
        private String Style;

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
    }
}
