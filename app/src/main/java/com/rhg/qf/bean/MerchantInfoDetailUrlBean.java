package com.rhg.qf.bean;

import java.util.List;

/*
 *desc 商家详情UrlBean
 *author rhg
 *time 2016/7/10 15:32
 *email 1013773046@qq.com
 */
public class MerchantInfoDetailUrlBean {

    /**
     * result : 0
     * msg : 请求成功
     * total : 1
     * rows : [{"Name":"黄焖鸡米饭","Address":"杭州下沙","Message":"卖黄焖鸡米饭的店","Phonenumber":"123456789"}]
     */

    private int result;
    private String msg;
    private int total;
    /**
     * Name : 黄焖鸡米饭
     * Address : 杭州下沙
     * Message : 卖黄焖鸡米饭的店
     * Phonenumber : 123456789
     */

    private List<MerchantInfoDetailBean> rows;

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

    public List<MerchantInfoDetailBean> getRows() {
        return rows;
    }

    public void setRows(List<MerchantInfoDetailBean> rows) {
        this.rows = rows;
    }

    public static class MerchantInfoDetailBean {
        private String Name;
        private String Address;
        private String Message;
        private String Phonenumber;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getPhonenumber() {
            return Phonenumber;
        }

        public void setPhonenumber(String Phonenumber) {
            this.Phonenumber = Phonenumber;
        }
    }
}
