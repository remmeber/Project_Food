package com.rhg.qf.utils;

import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.datebase.AccountDBHelper;
import com.rhg.qf.datebase.AccountDao;

import java.util.List;

/**
 * desc:
 * author：remember
 * time：2016/7/19 23:55
 * email：1013773046@qq.com
 */
public class AddressUtil {
    /**
     * desc:插入保存地址
     * author：remember
     * time：2016/6/5 13:45
     * email：1013773046@qq.com
     */
    public static void insertAddress(AddressUrlBean.AddressBean addressBean) {
        AccountDao.getInstance().saveAddress(addressBean);
    }

    /**
     * desc:删除数据库中的一条地址
     * author：remember
     * time：2016/6/5 11:56
     * email：1013773046@qq.com
     */
    public static void deleteOneAddress(String whereArg) {
        AccountDao.getInstance().deleteItemInTableById(AccountDBHelper.Q_ADDRESS_TABLE, AppConstants.ADDRESS_ID+" =?",
                new String[]{whereArg});
    }

    /**
     * desc:删除数据库中所有的地址
     * author：remember
     * time：2016/6/5 11:58
     * email：1013773046@qq.com
     */
    public static void deleteAllAddress() {
        AccountDao.getInstance().deleteAllItemInTable(AccountDBHelper.Q_ADDRESS_TABLE);
    }

    /**
     * desc:更新数据库一条地址
     * author：remember
     * time：2016/6/5 12:04
     * email：1013773046@qq.com
     */
    public static void updateAddress(String whereArg, AddressUrlBean.AddressBean addressBean) {
        AccountDao.getInstance().updateAddress(whereArg, addressBean);
    }

    /**
     * desc:获取地址列表
     * author：remember
     * time：2016/6/5 12:05
     * email：1013773046@qq.com
     */
    public static List<AddressUrlBean.AddressBean> getAddressList() {
        return AccountDao.getInstance().getAddressList();
    }

    /**
     * desc:获取默认地址
     * author：remember
     * time：2016/7/20 0:15
     * email：1013773046@qq.com
     */
    public static AddressUrlBean.AddressBean getDefaultAddress() {
        return AccountDao.getInstance().getAddressByDefault();
    }
}
