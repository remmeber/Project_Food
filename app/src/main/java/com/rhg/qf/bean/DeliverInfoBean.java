package com.rhg.qf.bean;

/*
 *desc
 *author rhg
 *time 2016/10/20 14:08
 *email 1013773046@qq.com
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 *desc 跑腿员信息
 *author rhg
 *time 2016/10/20 14:15
 *email 1013773046@qq.com
 */
public class DeliverInfoBean extends BaseBean{

    /**
     * total : 1
     * rows : [{"ID":"10","DName":"快递员1","PersonId":"1234567890002456","Phonenumber":"1383838438","Pwd":"123456","Area":"嵊州不知道哪里","ClientID":"10"}]
     */

    private int total;
    /**
     * ID : 10
     * DName : 快递员1
     * PersonId : 1234567890002456
     * Phonenumber : 1383838438
     * Pwd : 123456
     * Area : 嵊州不知道哪里
     * ClientID : 10
     */

    @SerializedName("rows")
    private List<InfoBean> DeliverInfo;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<InfoBean> getDeliverInfo() {
        return DeliverInfo;
    }

    public void setDeliverInfo(List<InfoBean> DeliverInfo) {
        this.DeliverInfo = DeliverInfo;
    }

    public static class InfoBean {
        private String ID;
        private String DName;
        private String PersonId;
        private String Phonenumber;
        private String Pwd;
        private String Area;
        private String ClientID;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getDName() {
            return DName;
        }

        public void setDName(String DName) {
            this.DName = DName;
        }

        public String getPersonId() {
            return PersonId;
        }

        public void setPersonId(String PersonId) {
            this.PersonId = PersonId;
        }

        public String getPhonenumber() {
            return Phonenumber;
        }

        public void setPhonenumber(String Phonenumber) {
            this.Phonenumber = Phonenumber;
        }

        public String getPwd() {
            return Pwd;
        }

        public void setPwd(String Pwd) {
            this.Pwd = Pwd;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String Area) {
            this.Area = Area;
        }

        public String getClientID() {
            return ClientID;
        }

        public void setClientID(String ClientID) {
            this.ClientID = ClientID;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "ID='" + ID + '\'' +
                    ", DName='" + DName + '\'' +
                    ", PersonId='" + PersonId + '\'' +
                    ", Phonenumber='" + Phonenumber + '\'' +
                    ", Pwd='" + Pwd + '\'' +
                    ", Area='" + Area + '\'' +
                    ", ClientID='" + ClientID + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DeliverInfoBean{" +
                "total=" + total +
                ", DeliverInfo=" + DeliverInfo +
                '}';
    }
}
