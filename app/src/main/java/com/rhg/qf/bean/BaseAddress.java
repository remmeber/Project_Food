package com.rhg.qf.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rhg on 2016/12/29.
 */

public class BaseAddress implements Parcelable {
    public String Name;
    public String Phone;
    public String Address;
    public String Detail;



    @Override
    public String toString() {
        return "BaseAddress{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Address='" + Address + '\'' +
                ", Detail='" + Detail + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.Phone);
        dest.writeString(this.Address);
        dest.writeString(this.Detail);
    }

    public BaseAddress() {
    }

    protected BaseAddress(Parcel in) {
        this.Name = in.readString();
        this.Phone = in.readString();
        this.Address = in.readString();
        this.Detail = in.readString();
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getDetail() {
        return Detail;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
