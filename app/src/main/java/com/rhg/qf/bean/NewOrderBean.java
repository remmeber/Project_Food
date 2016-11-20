package com.rhg.qf.bean;

import java.util.List;

/**
 * desc:用于新建订单
 * author：remember
 * time：2016/7/11 0:27
 * email：1013773046@qq.com
 */
public class NewOrderBean {

    /**
     * Client : 1
     * Receiver : 张三
     * Address : 杭州下沙
     * Phone : 1383838438
     * Price : 200
     * Food : [{"ID":"1","Num":"1"},{"ID":"2","Num":"1"}]
     */

    private String Client;
    private String Receiver;
    private String Address;
    private String Phone;
    private String Price;
    private List<FoodBean> Food;

    public String getClient() {
        return Client;
    }

    public void setClient(String Client) {
        this.Client = Client;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }


    public List<FoodBean> getFood() {
        return Food;
    }

    public void setFood(List<FoodBean> Food) {
        this.Food = Food;
    }

    @Override
    public String toString() {
        return "NewOrderBean{" +
                "Client='" + Client + '\'' +
                ", Receiver='" + Receiver + '\'' +
                ", Address='" + Address + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Price='" + Price + '\'' +
                ", Food=" + Food +
                '}';
    }

    public static class FoodBean {
        private String ID;
        private String Num;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getNum() {
            return Num;
        }

        public void setNum(String Num) {
            this.Num = Num;
        }

        @Override
        public String toString() {
            return "FoodBean{" +
                    "ID='" + ID + '\'' +
                    ", Num='" + Num + '\'' +
                    '}';
        }
    }
}
