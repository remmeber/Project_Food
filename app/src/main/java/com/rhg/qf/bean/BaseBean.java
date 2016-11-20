package com.rhg.qf.bean;

/**
 * 作者：rememberon 2016/7/10
 * 邮箱：1013773046@qq.com
 */
public class BaseBean {

    /**
     * result : 0
     * msg : 修改成功！
     */

    private int result;
    private String msg;

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

    @Override
    public String toString() {
        return "BaseBean{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                '}';
    }
}
