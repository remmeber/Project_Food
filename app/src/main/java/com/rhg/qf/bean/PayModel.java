package com.rhg.qf.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * desc:
 * author：remember
 * time：2016/7/11 0:10
 * email：1013773046@qq.com
 */
public class PayModel extends BaseAddress {

    public ArrayList<PayBean> payBeanList;

    public ArrayList<PayBean> getPayBeanList() {
        return payBeanList;
    }

    public void setPayBeanList(ArrayList<PayBean> payBeanList) {
        this.payBeanList = payBeanList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.payBeanList);
    }

    public PayModel() {
    }

    protected PayModel(Parcel in) {
        super(in);
        this.payBeanList = in.createTypedArrayList(PayBean.CREATOR);
    }

    public static final Creator<PayModel> CREATOR = new Creator<PayModel>() {
        @Override
        public PayModel createFromParcel(Parcel source) {
            return new PayModel(source);
        }

        @Override
        public PayModel[] newArray(int size) {
            return new PayModel[size];
        }
    };

    public static class PayBean implements Parcelable {
        public static final Creator<PayBean> CREATOR = new Creator<PayBean>() {
            @Override
            public PayBean createFromParcel(Parcel in) {
                return new PayBean(in);
            }

            @Override
            public PayBean[] newArray(int size) {
                return new PayBean[size];
            }
        };
        String merchantName;
        String merchantId;
        String productId;
        String productName;
        String productPrice;
        String deliverFee;
        String productPic;
        String productNumber;
        boolean isChecked;

        protected PayBean(Parcel in) {
            merchantName = in.readString();
            merchantId = in.readString();
            productId = in.readString();
            productName = in.readString();
            productPrice = in.readString();
            deliverFee = in.readString();
            productPic = in.readString();
            productNumber = in.readString();
            isChecked = in.readByte() != 0;
        }

        public PayBean() {
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getDeliverFee() {
            return deliverFee;
        }

        public void setDeliverFee(String deliverFee) {
            this.deliverFee = deliverFee;
        }

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(merchantName);
            parcel.writeString(merchantId);
            parcel.writeString(productId);
            parcel.writeString(productName);
            parcel.writeString(productPrice);
            parcel.writeString(deliverFee);
            parcel.writeString(productPic);
            parcel.writeString(productNumber);
            parcel.writeByte((byte) (isChecked ? 1 : 0));
        }
    }

}
