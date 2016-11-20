package com.rhg.qf.datebase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.FoodInfoBean;
import com.rhg.qf.bean.ShoppingCartBean;
import com.rhg.qf.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:购物车数据口DAO
 * author：remember
 * time：2016/5/28 16:41
 * email：1013773046@qq.com
 */
public class AccountDao {

    private volatile static AccountDao instance = null;
    private SQLiteDatabase db;
    private Cursor cursor;

    /**
     * 获取SimpleDemoDB实例
     */
    public static AccountDao getInstance() {
        if (instance == null) {
            synchronized (AccountDao.class) {
                if (instance == null) {
                    instance = new AccountDao();
                }
            }
        }
        return instance;
    }


    public void close() {
        if (db != null) {
            db.close();
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public int getTableCountByName(String tableName) {
        db = AccountDBHelper.getInstance().getReadableDatabase();
        cursor = db.rawQuery("select count(*) from " + tableName, null);
        int count = 0;
        //游标移到第一条记录准备获取数据
        if (cursor.moveToFirst()) {
            // 获取数据中的LONG类型数据
            count = (int) cursor.getLong(0);
        }
        close();
        return count;
    }

   /* public int getGoodsCount() {
        db = AccountDBHelper.getInstance().getReadableDatabase();
        cursor = db.rawQuery("select count(*) from " + AccountDBHelper.Q_SHOPPING_CART_TABLE, null);
        int count = 0;
        //游标移到第一条记录准备获取数据
        if (cursor.moveToFirst()) {
            // 获取数据中的LONG类型数据
            count = (int) cursor.getLong(0);
        }
        close();
        return count;
    }*/


    /**
     * @param Name ShoppingCartBean.KEY_FOOD_ID or ShoppingCartBean.KEY_MERCHANT_ID
     * @param ID   productId or merchantId
     * @return
     */
    public boolean isExist(String Name, String ID) {
        if (ID != null && "".equals(ID)) {
            return false;
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        cursor = db.query(AccountDBHelper.Q_SHOPPING_CART_TABLE, null, Name + "=?", new String[]{ID}, null, null, null);
//        Log.i("RHG", "cursor is :" + (cursor == null));
        if (cursor == null)
            return false;
        boolean isExist = cursor.moveToFirst();
        close();
        return isExist;
    }

    /**
     * 添加购物车商品信息
     *
     * @param foodInfoBean The food info object which should be added in to db
     */
    public void saveCartInfo(FoodInfoBean foodInfoBean) {
        if (foodInfoBean == null) {
            return;
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ShoppingCartBean.KEY_FOOD_ID, foodInfoBean.getFoodId() == null ? "" : foodInfoBean.getFoodId());
        values.put(ShoppingCartBean.KEY_FOOD_NAME, foodInfoBean.getFoodName());
        values.put(ShoppingCartBean.KEY_MERCHANT_NAME, foodInfoBean.getMerchantName());
        values.put(ShoppingCartBean.KEY_FOOD_URI, foodInfoBean.getFoodUri());
        values.put(ShoppingCartBean.KEY_NUM, foodInfoBean.getFoodNum());
        values.put(ShoppingCartBean.KEY_FOOD_PRICE, foodInfoBean.getFoodPrice());
        values.put(ShoppingCartBean.KEY_MERCHANT_ID, foodInfoBean.getMerchantId());
        db.insert(AccountDBHelper.Q_SHOPPING_CART_TABLE, null, values);
        close();
    }

    public void saveAddress(AddressUrlBean.AddressBean addressBean) {
        if (addressBean == null) {
            return;
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppConstants.ADDRESS_ID, addressBean.getID());
        values.put(AppConstants.NAME_FOR_ADDRESS, addressBean.getName());
        values.put(AppConstants.PHONE_FOR_ADDRESS, addressBean.getPhone());
        values.put(AppConstants.ADDRESS, addressBean.getAddress());
        values.put(AppConstants.ADDRESS_DETAIL, addressBean.getDetail());
        values.put(AppConstants.ADDRESS_DEFAULT, addressBean.getDefault());
        db.insert(AccountDBHelper.Q_ADDRESS_TABLE, null, values);
        close();
    }


    public void deleteItemInTableById(String Table, String conditionId, String[] condition) {
        if (condition == null) {
            return;
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        int delete = db.delete(Table, conditionId, condition);
        close();
    }

    public void deleteAllItemInTable(String Table) {
        db = AccountDBHelper.getInstance().getReadableDatabase();
        db.delete(Table, null, null);
        close();
    }

    /*public void deleteItemList(String Table, List<String> itemList) {
        if (itemList == null) {
            return;
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        for (int i = 0; i < itemList.size(); i++) {
            db.delete(AccountDBHelper.Q_SHOPPING_CART_TABLE, ShoppingCartBean.KEY_FOOD_ID + " =?", new String[]{itemList.get(i)});
        }
        close();
    }*/

    /**
     * 修改购物车中某件商品的信息
     *
     * @param productID 规格ID
     * @param num       商品数量
     */
    public void updateFoodNum(String merchantID, String productID, String num) {
        if (productID == null || "".equals(productID) || num == null || "".equals(num)) {
            return;
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        if (!"".equals(productID) && !"".equals(num)) {
            values.put(ShoppingCartBean.KEY_NUM, num);
//            Log.i("RHG", "MODIFY ID:" + productID);
            db.update(AccountDBHelper.Q_SHOPPING_CART_TABLE, values, ShoppingCartBean.KEY_FOOD_ID + "=? and " + ShoppingCartBean.KEY_MERCHANT_ID + "=?", new String[]{productID, merchantID});
        }
        close();
    }

    public void updateAddress(String whereArg, AddressUrlBean.AddressBean updateItems) {
        if (whereArg == null || "".equals(whereArg) || updateItems == null) {
            return;
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppConstants.NAME_FOR_ADDRESS, updateItems.getName());
        values.put(AppConstants.PHONE_FOR_ADDRESS, updateItems.getPhone());
        values.put(AppConstants.ADDRESS, updateItems.getAddress());
        db.update(AccountDBHelper.Q_ADDRESS_TABLE, values, AppConstants.ADDRESS_ID + "=?",
                new String[]{whereArg});
        close();
    }

    public String getNumByFoodID(String productID) {
        if (productID == null) {
            return "1";
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        cursor = db.query(AccountDBHelper.Q_SHOPPING_CART_TABLE, new String[]{ShoppingCartBean.KEY_NUM}, ShoppingCartBean.KEY_FOOD_ID + "=?", new String[]{productID}, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        close();
        return "1";
    }

    /**
     * 查询数据库中的购物车中的商品信息
     *
     * @return 购物车中的商品信息
     */
    public List<FoodInfoBean> getCartList() {
        db = AccountDBHelper.getInstance().getReadableDatabase();
        List<FoodInfoBean> mList = new ArrayList<>();
        Cursor cursor = db.query(AccountDBHelper.Q_SHOPPING_CART_TABLE,
                new String[]{ShoppingCartBean.KEY_FOOD_ID, ShoppingCartBean.KEY_FOOD_NAME, ShoppingCartBean.KEY_MERCHANT_NAME,
                        ShoppingCartBean.KEY_FOOD_URI, ShoppingCartBean.KEY_NUM, ShoppingCartBean.KEY_FOOD_PRICE, ShoppingCartBean.KEY_MERCHANT_ID},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String productID = cursor.getString(0);
                String foodUri = cursor.getString(1);
                String merchantName = cursor.getString(2);
                String foodNum = cursor.getString(3);
                String foodName = cursor.getString(4);
                String foodPrice = cursor.getString(5);
                String merchantId = cursor.getString(6);
                if (productID != null && !"".equals(productID)) {
                    FoodInfoBean foodInfoBean = new FoodInfoBean(productID, foodName, merchantName, foodUri, foodNum, foodPrice, merchantId);
//                    Log.i("RHG", "OUT:" + foodInfoBean.toString());
                    mList.add(foodInfoBean);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    public List<AddressUrlBean.AddressBean> getAddressList() {
        List<AddressUrlBean.AddressBean> addressBeanList = new ArrayList<>();
        String[] columns = new String[]{
                AppConstants.ADDRESS_ID,
                AppConstants.NAME_FOR_ADDRESS,
                AppConstants.PHONE_FOR_ADDRESS,
                AppConstants.ADDRESS,
                AppConstants.ADDRESS_DETAIL,
                AppConstants.ADDRESS_DEFAULT};
        String selection = AppConstants.ADDRESS_ID + "=?";
        db = AccountDBHelper.getInstance().getReadableDatabase();
        cursor = db.query(AccountDBHelper.Q_ADDRESS_TABLE, columns, selection, null, null,
                null, null);
        if (cursor.moveToFirst()) {
            do {
                AddressUrlBean.AddressBean addressBean = new AddressUrlBean.AddressBean();
                String id = cursor.getString(0);
                String Name = cursor.getString(1);
                String phone = cursor.getString(2);
                String address = cursor.getString(3);
                String detail = cursor.getString(4);
                String address_default = cursor.getString(5);
                if (id != null && !"".equals(id)) {
                    addressBean.setID(id);
                }
                if (Name != null && !"".equals(Name)) {
                    addressBean.setName(Name);
                }
                if (phone != null && !"".equals(phone)) {
                    addressBean.setPhone(phone);
                }
                if (address != null && !"".equals(address)) {
                    addressBean.setAddress(address);
                }
                addressBean.setDetail(detail);
                addressBean.setDefault(address_default);
                addressBeanList.add(addressBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return addressBeanList;
    }


    public AddressUrlBean.AddressBean getAddressByDefault() {
        AddressUrlBean.AddressBean _defaultAddress = null;
        String selection = AppConstants.ADDRESS_DEFAULT + "=1";
        String[] columns = new String[]{
                AppConstants.ADDRESS_ID,
                AppConstants.NAME_FOR_ADDRESS,
                AppConstants.PHONE_FOR_ADDRESS,
                AppConstants.ADDRESS,
                AppConstants.ADDRESS_DETAIL,
                AppConstants.ADDRESS_DEFAULT};
        db = AccountDBHelper.getInstance().getReadableDatabase();
        cursor = db.query(AccountDBHelper.Q_ADDRESS_TABLE, columns,
                selection, null, null,
                null, null);
        if (cursor.moveToFirst()) {
            do {
                _defaultAddress = new AddressUrlBean.AddressBean();
                String id = cursor.getString(0);
                String Name = cursor.getString(1);
                String phone = cursor.getString(2);
                String address = cursor.getString(3);
                String detail = cursor.getString(4);
                String address_default = cursor.getString(5);
                _defaultAddress.setID(id);
                _defaultAddress.setName(Name);
                _defaultAddress.setPhone(phone);
                _defaultAddress.setAddress(address);
                _defaultAddress.setDetail(detail);
                _defaultAddress.setDefault(address_default);
            } while (cursor.moveToNext());
        }
        return _defaultAddress;
    }

    public List<String> queryHistory(String searchContent) {
        if (searchContent == null) {
            return null;
        }
        List<String> mList = new ArrayList<>();
        db = AccountDBHelper.getInstance().getReadableDatabase();
        cursor = db.query(AccountDBHelper.Q_SEARCH_HISTORY_TABLE, new String[]{"searched"}, "searched like ?",
                new String[]{"%" + searchContent + "%"}, null, null, null);
        if (cursor.moveToLast()) {
            do {
                String historyContent = cursor.getString(0);
                if (historyContent != null && !"".equals(historyContent)) {
                    mList.add(historyContent);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return mList;
    }

    public List<String> getSearchHistoryList() {
        db = AccountDBHelper.getInstance().getReadableDatabase();
        List<String> mList = new ArrayList<>();
        Cursor cursor = db.query(AccountDBHelper.Q_SEARCH_HISTORY_TABLE,
                new String[]{"searched"},
                null, null, null, null, null);
        if (cursor.moveToLast()) {
            do {
                String productID = cursor.getString(0);
                if (productID != null && !"".equals(productID)) {
                    mList.add(productID);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return mList;
    }

    public void saveSearchHistory(String historyContent) {
        if (historyContent == null) {
            return;
        }
        db = AccountDBHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("searched", historyContent);
        db.insert(AccountDBHelper.Q_SEARCH_HISTORY_TABLE, null, values);
        close();
    }


}
