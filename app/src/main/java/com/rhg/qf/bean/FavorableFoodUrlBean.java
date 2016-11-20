package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:主页推荐食物网络请求数据模型
 * author：remember
 * time：2016/5/28 16:12
 * email：1013773046@qq.com
 */
public class FavorableFoodUrlBean {
    /**
     * total : 6
     * rows : [{"ID":"1","Title":"楦绮変笣1","Src":"http://www.zousitanghulu.com/json/pic/head1.jpg","Jump":"this is Jump Src"},{"ID":"2","Title":"楦绮変笣2","Src":"http://www.zousitanghulu.com/json/pic/head2.jpg","Jump":"this is Jump Src"},{"ID":"3","Title":"楦绮変笣3","Src":"http://www.zousitanghulu.com/json/pic/head3.jpg","Jump":"this is Jump Src"},{"ID":"4","Title":"楦绮変笣4","Src":"http://www.zousitanghulu.com/json/pic/head4.jpg","Jump":"this is Jump Src"},{"ID":"5","Title":"楦绮変笣5","Src":"http://www.zousitanghulu.com/json/pic/head5.jpg","Jump":"this is Jump Src"},{"ID":"6","Title":"楦绮変笣6","Src":"http://www.zousitanghulu.com/json/pic/head6.jpg","Jump":"this is Jump Src"}]
     */

    private String total;
    /**
     * ID : 1
     * Title : 楦绮変笣1
     * Src : http://www.zousitanghulu.com/json/pic/head1.jpg
     * Jump : this is Jump Src
     */

    private List<FavorableFoodEntity> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<FavorableFoodEntity> getRows() {
        return rows;
    }

    public void setRows(List<FavorableFoodEntity> rows) {
        this.rows = rows;
    }

    public static class FavorableFoodEntity {
        private String ID;
        private String Title;
        private String Src;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getSrc() {
            return Src;
        }

        public void setSrc(String Src) {
            this.Src = Src;
        }
    }


    /*private String ID;
    private String Title;
    private String imageUrl;
    private int headercolor;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getHeadercolor() {
        return headercolor;
    }

    public void setHeadercolor(int headercolor) {
        this.headercolor = headercolor;
    }

    @Override
    public String toString() {
        return "FavorableFoodUrlBean{ID=" + ID +
                "imageUrl=" + imageUrl +
                ", Title='" + Title + '\'' +
                '}';
    }*/
}
