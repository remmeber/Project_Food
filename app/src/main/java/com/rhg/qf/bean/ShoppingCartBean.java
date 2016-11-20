package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:购物车模型  //todo 后期修改继承自店铺模型
 * author：remember
 * time：2016/5/28 16:39
 * email：1013773046@qq.com
 */
public class ShoppingCartBean {
    public static final String KEY_FOOD_ID = "foodId";
    public static final String KEY_NUM = "productNum";
    public static final String KEY_MERCHANT_NAME = "merchantName";
    public static final String KEY_FOOD_NAME = "foodName";
    public static final String KEY_FOOD_URI = "foodUri";
    public static final String KEY_FOOD_PRICE = "foodPrice";
    public static final String KEY_MERCHANT_ID = "merchantId";

    /**
     * 组是否被选中
     */
    private boolean isGroupSelected;

    /**
     * 店铺名称
     */
    private String merchantName;

    /**
     * 店铺ID
     */
    private String merID;
    /**
     * 是否失效列表
     */
    private boolean isInvalidList;

    private boolean isAllGoodsInvalid;

    private List<Goods> goods;

    public boolean isGroupSelected() {
        return isGroupSelected;
    }

    public void setIsGroupSelected(boolean groupSelected) {
        isGroupSelected = groupSelected;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerID() {
        return merID;
    }

    public void setMerID(String merID) {
        this.merID = merID;
    }

    public boolean isInvalidList() {
        return isInvalidList;
    }

    public void setInvalidList(boolean invalidList) {
        isInvalidList = invalidList;
    }

    public boolean isAllGoodsInvalid() {
        return isAllGoodsInvalid;
    }

    public void setAllGoodsInvalid(boolean allGoodsInvalid) {
        isAllGoodsInvalid = allGoodsInvalid;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }


    /**
     * 商品类，本地用变量应该加上标志 ' _local '
     */
    public static class Goods {
        /**
         * 数量
         */
        private String number = "1";
        /**
         * 商品ID
         */
        private String goodsID;
        /**
         * 商品名称
         */
        private String goodsName;
        /**
         * 商品宣传图片
         */
        private String goodsLogoUrl;
        /** 商品规格 */
        /*private String pdtDesc;*/
        /**
         * 市场价，原价
         */
        private String mkPrice;
        /**
         * 价格，当前价格
         */
        private String price;

        private String Fee;
        /**
         * 是否失效,0删除(失效),1正常
         */
        private String status;
//        /**
//         * 是否是编辑状态
//         */
//        private boolean isEditing;
        /**
         * 是否被选中
         */
        private boolean isChildSelected;
        /**
         * 规格ID
         */
        private String productID;

        /***/
        private String sellPloyID;

        /**
         * 是否是失效列表的子项
         */
        private boolean isInvalidItem;

        /**
         * 是否属于
         */
        private boolean isBelongInvalidList;


        public boolean isBelongInvalidList() {
            return isBelongInvalidList;
        }

        public void setIsBelongInvalidList(boolean isBelongInvalidList) {
            this.isBelongInvalidList = isBelongInvalidList;
        }

        public boolean isInvalidItem() {
            return isInvalidItem;
        }

        public void setIsInvalidItem(boolean isInvalidItem) {
            this.isInvalidItem = isInvalidItem;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSellPloyID() {
            return sellPloyID;
        }

        public void setSellPloyID(String sellPloyID) {
            this.sellPloyID = sellPloyID;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        /*public boolean isEditing() {
            return isEditing;
        }

        public void setIsEditing(boolean isEditing) {
            this.isEditing = isEditing;
        }*/

        public boolean isChildSelected() {
            return isChildSelected;
        }

        public void setIsChildSelected(boolean isChildSelected) {
            this.isChildSelected = isChildSelected;
        }

        public String getGoodsID() {
            return goodsID;
        }

        public void setGoodsID(String goodsID) {
            this.goodsID = goodsID;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsLogoUrl() {
            return goodsLogoUrl;
        }

        public void setGoodsLogoUrl(String goodsLogoUrl) {
            this.goodsLogoUrl = goodsLogoUrl;
        }

        /*public String getPdtDesc() {
            return pdtDesc;
        }

        public void setPdtDesc(String pdtDesc) {
            this.pdtDesc = pdtDesc;
        }*/

        public String getMkPrice() {
            return mkPrice;
        }

        public void setMkPrice(String mkPrice) {
            this.mkPrice = mkPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getFee() {
            return Fee;
        }

        public void setFee(String fee) {
            Fee = fee;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

    }
}
