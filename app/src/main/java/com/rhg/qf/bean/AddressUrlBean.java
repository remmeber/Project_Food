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
        private String ID;
        private String Client;
        private String Default;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.ID);
            dest.writeString(this.Client);
            dest.writeString(this.Default);
        }

        public AddressBean() {
        }

        protected AddressBean(Parcel in) {
            super(in);
            this.ID = in.readString();
            this.Client = in.readString();
            this.Default = in.readString();
        }

        public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
            @Override
            public AddressBean createFromParcel(Parcel source) {
                return new AddressBean(source);
            }

            @Override
            public AddressBean[] newArray(int size) {
                return new AddressBean[size];
            }
        };

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getClient() {
            return Client;
        }

        public void setClient(String client) {
            Client = client;
        }

        public String getDefault() {
            return Default;
        }

        public void setDefault(String aDefault) {
            Default = aDefault;
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
