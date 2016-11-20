package com.rhg.qf.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * desc:
 * author：remember
 * time：2016/6/17 20:11
 * email：1013773046@qq.com
 */
public class AddressUrlBean {

    /**
     * result : 0
     * msg : 请求成功
     * total : 9
     * rows : [{"ID":"1","Client":"1","Name":"zz","Phone":"1866730000","Address":"浙江杭州下沙"},{"ID":"5","Client":"1","Name":"李四","Phone":"123456","Address":"浙江杭州下沙"},{"ID":"6","Client":"1","Name":"李四","Phone":"123456","Address":"浙江杭州下沙"},{"ID":"4","Client":"1","Name":"张三","Phone":"123456","Address":"浙江杭州下沙"},{"ID":"7","Client":"1","Name":"李四","Phone":"123456","Address":"浙江杭州下沙"},{"ID":"8","Client":"1","Name":"李四","Phone":"123456","Address":"浙江杭州下沙"},{"ID":"9","Client":"1","Name":"李四","Phone":"123456","Address":"浙江杭州下沙"},{"ID":"10","Client":"1","Name":"李四","Phone":"123456","Address":"浙江杭州下沙"},{"ID":"11","Client":"1","Name":"李四","Phone":"123456","Address":"浙江杭州下沙"}]
     */

    private int result;
    private String msg;
    private int total;
    /**
     * ID : 1
     * Client : 1
     * Name : zz
     * Phone : 1866730000
     * Address : 浙江杭州下沙
     */

    private List<AddressBean> rows;

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

    public List<AddressBean> getRows() {
        return rows;
    }

    public void setRows(List<AddressBean> rows) {
        this.rows = rows;
    }

    public static class AddressBean implements Parcelable {
        public static final Parcelable.Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
            @Override
            public AddressBean createFromParcel(Parcel source) {
                return new AddressBean(source);
            }

            @Override
            public AddressBean[] newArray(int size) {
                return new AddressBean[size];
            }
        };
        private String ID;
        private String Client;
        private String Name;
        private String Phone;
        private String Address;
        private String Default;
        private String Detail;

        public AddressBean() {
        }

        public AddressBean(String name, String phone, String address, String detail) {
            Name = name;
            Phone = phone;
            Address = address;
            Detail = detail;
        }

        public AddressBean(Parcel source) {
            ID = source.readString();
            Client = source.readString();
            Name = source.readString();
            Phone = source.readString();
            Address = source.readString();
            Default = source.readString();
            Detail = source.readString();
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getClient() {
            return Client;
        }

        public void setClient(String Client) {
            this.Client = Client;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getDefault() {
            return Default;
        }

        public void setDefault(String Default) {
            this.Default = Default;
        }

        public String getDetail() {
            return Detail;
        }

        public void setDetail(String detail) {
            Detail = detail;
        }

        @Override
        public String toString() {
            return "AddressBean{" +
                    "ID='" + ID + '\'' +
                    ", Client='" + Client + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Phone='" + Phone + '\'' +
                    ", Address='" + Address + '\'' +
                    ", Default='" + Default + '\'' +
                    ", Detail='" + Detail + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeString(ID);
            parcel.writeString(Client);
            parcel.writeString(Name);
            parcel.writeString(Phone);
            parcel.writeString(Address);
            parcel.writeString(Default);
            parcel.writeString(Detail);
        }
    }
}
