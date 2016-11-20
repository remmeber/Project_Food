package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:轮播图片的网络访问数据模型，网络请求后，需要将该类转换为{@link BannerTypeBean}
 * author：remember
 * time：2016/5/28 16:23
 * email：1013773046@qq.com
 */
public class BannerTypeUrlBean {


    String total;
    List<BannerEntity> rows;
    /**
     * result : 0
     * msg : 请求成功
     */

    private int result;
    private String msg;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<BannerEntity> getRows() {
        return rows;
    }

    public void setRows(List<BannerEntity> rows) {
        this.rows = rows;
    }

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

    public static class BannerEntity {

        String id;
        String Src;

        public String getID() {
            return id;
        }

        public void setID(String id) {
            this.id = id;
        }

        public String getSrc() {
            return Src;
        }

        public void setSrc(String src) {
            Src = src;
        }

        @Override
        public String toString() {
            return "ID: " + id + " Src: " + Src;
        }
    }

    @Override
    public String toString() {
        return "BannerTypeUrlBean{" +
                "total='" + total + '\'' +
                ", rows=" + rows.toString() +
                ", result=" + result +
                ", msg='" + msg + '\'' +
                '}';
    }
}
