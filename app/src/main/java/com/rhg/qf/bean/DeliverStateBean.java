package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:
 * author：remember
 * time：2016/7/10 12:16
 * email：1013773046@qq.com
 */
public class DeliverStateBean {

    /**
     * result : 0
     * msg : 请求成功
     * total : 1
     * rows : [{"Style":"已完成"}]
     */

    private int result;
    private String msg;
    private int total;
    /**
     * Style : 已完成
     */

    private List<RowsBean> rows;

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String Style;

        public String getStyle() {
            return Style;
        }

        public void setStyle(String Style) {
            this.Style = Style;
        }
    }
}
