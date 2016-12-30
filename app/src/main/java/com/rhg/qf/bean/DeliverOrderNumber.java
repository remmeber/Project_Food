package com.rhg.qf.bean;

import java.util.List;

/**
 * Created by rhg on 2016/12/30.
 */

public class DeliverOrderNumber extends BaseBean {
    List<CountBean> rows;

    public List<CountBean> getRows() {
        return rows;
    }

    public void setRows(List<CountBean> rows) {
        this.rows = rows;
    }

    public class CountBean {
        String num;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "CountBean{" +
                    "num='" + num + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DeliverOrderNumber{" +
                "rows=" + rows +
                '}';
    }
}
