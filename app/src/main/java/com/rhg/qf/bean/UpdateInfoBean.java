package com.rhg.qf.bean;

import java.util.List;

/**
 * 作者：rememberon 2017/1/23
 * 邮箱：1013773046@qq.com
 */

public class UpdateInfoBean extends BaseBean {
    List<UpdateInfo> rows;

    public List<UpdateInfo> getRows() {
        return rows;
    }

    public void setRows(List<UpdateInfo> rows) {
        this.rows = rows;
    }
}
