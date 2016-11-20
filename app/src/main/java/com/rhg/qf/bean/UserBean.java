package com.rhg.qf.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * desc: 跑腿源信息模型
 * author：remember
 * time：2016/6/17 14:48
 * email：1013773046@qq.com
 */
public class UserBean {

    /**
     * total : 1
     * rows : [{"ID":"1","CName":"19216801","PersonId":"330424","Phonenumber":"1860000000","Pwd":"123","Pic":"http://zousitanghulu.com/Pic/ClientPic/19216801.jpg"}]
     */

    private int total;
    /**
     * ID : 1
     * CName : 19216801
     * PersonId : 330424
     * Phonenumber : 1860000000
     * Pwd : 123
     * Pic : http://zousitanghulu.com/Pic/ClientPic/19216801.jpg
     */

    private List<User> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<User> getRows() {
        return rows;
    }

    public void setRows(List<User> rows) {
        this.rows = rows;
    }

    public static class User implements Parcelable {
        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };
        private String ID;
        private String CName;
        private String PersonId;
        private String Phonenumber;
        private String Pwd;
        private String Pic;

        public User() {
        }

        protected User(Parcel in) {
            ID = in.readString();
            CName = in.readString();
            PersonId = in.readString();
            Phonenumber = in.readString();
            Pwd = in.readString();
            Pic = in.readString();
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCName() {
            return CName;
        }

        public void setCName(String CName) {
            this.CName = CName;
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

        public String getPic() {
            return Pic;
        }

        public void setPic(String Pic) {
            this.Pic = Pic;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(ID);
            parcel.writeString(CName);
            parcel.writeString(PersonId);
            parcel.writeString(Phonenumber);
            parcel.writeString(Pwd);
            parcel.writeString(Pic);
        }
    }
}
