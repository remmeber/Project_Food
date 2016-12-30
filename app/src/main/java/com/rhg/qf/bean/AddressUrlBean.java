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

    public static class AddressBean extends BaseAddress {
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
        private String Default;

        public AddressBean() {
        }


        public AddressBean(Parcel source) {
            ID = source.readString();
            Client = source.readString();
            Default = source.readString();
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

        public String getDefault() {
            return Default;
        }

        public void setDefault(String Default) {
            this.Default = Default;
        }

        @Override
        public String toString() {
            return "AddressBean{" +
                    "ID='" + ID + '\'' +
                    ", Client='" + Client + '\'' +
                    ", Default='" + Default + '\'' +
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
            parcel.writeString(Default);
        }
    }

    @Override
    public String toString() {
        return "AddressUrlBean{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", total=" + total +
                ", rows=" + rows.toString() +
                '}';
    }
}
