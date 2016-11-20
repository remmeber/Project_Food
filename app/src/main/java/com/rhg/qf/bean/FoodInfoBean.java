package com.rhg.qf.bean;

/*
 *desc
 *author rhg
 *time 2016/11/10 14:33
 *email 1013773046@qq.com
 */

public class FoodInfoBean {

    String foodId ;
    String foodNum ;
    String merchantName ;
    String foodName;
    String foodUri ;
    String foodPrice;
    String merchantId;

    public FoodInfoBean(String foodId, String foodNum, String merchantName, String foodName, String foodUri,String foodPrice,String merchantId) {
        this.foodId = foodId;
        this.foodNum = foodNum;
        this.merchantName = merchantName;
        this.foodName = foodName;
        this.foodUri = foodUri;
        this.foodPrice = foodPrice;
        this.merchantId = merchantId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(String foodNum) {
        this.foodNum = foodNum;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodUri() {
        return foodUri;
    }

    public void setFoodUri(String foodUri) {
        this.foodUri = foodUri;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "FoodInfoBean{" +
                "foodId='" + foodId + '\'' +
                ", foodNum='" + foodNum + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", foodName='" + foodName + '\'' +
                ", foodUri='" + foodUri + '\'' +
                ", foodPrice='" + foodPrice + '\'' +
                ", merchantId='" + merchantId + '\'' +
                '}';
    }
}
