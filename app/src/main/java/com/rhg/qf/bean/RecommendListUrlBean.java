package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:主页店铺推荐网络请求数据模型
 * author：remember
 * time：2016/5/28 16:38
 * email：1013773046@qq.com
 */
public class RecommendListUrlBean {
    /**
     * totals : 3
     * rows : [{"ID":"1","Name":"KFC","Src":"http://www.zousitanghulu.com/json/pic/r1.jpg","Distance":"115m"},{"ID":"2","Name":"Pizza","Src":"http://www.zousitanghulu.com/json/pic/r1.jpg","Distance":"115m"},{"ID":"3","Name":"M璁�","Src":"http://www.zousitanghulu.com/json/pic/r1.jpg","Distance":"115m"}]
     */

    private int totals;
    /**
     * ID : 1
     * Name : KFC
     * Src : http://www.zousitanghulu.com/json/pic/r1.jpg
     * Distance : 115m
     */

    private List<RecommendShopBeanEntity> rows;

    public int getTotals() {
        return totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    public List<RecommendShopBeanEntity> getRows() {
        return rows;
    }

    public void setRows(List<RecommendShopBeanEntity> rows) {
        this.rows = rows;
    }

    public static class RecommendShopBeanEntity {
        private String ID;
        private String Name;
        private String Src;
        private String Distance;

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

        public String getSrc() {
            return Src;
        }

        public void setSrc(String Src) {
            this.Src = Src;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String Distance) {
            this.Distance = Distance;
        }
    }

   /* private String ID;
    private  String Name;
    private String Src;
    private String Distance;
    private String Jump;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSrc() {
        return Src;
    }

    public void setSrc(String src) {
        Src = src;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getJump() {
        return Jump;
    }

    public void setJump(String jump) {
        Jump = jump;
    }*/
}
