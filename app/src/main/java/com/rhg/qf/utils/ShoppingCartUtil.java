package com.rhg.qf.utils;

import android.widget.ImageView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.FoodInfoBean;
import com.rhg.qf.bean.PayModel;
import com.rhg.qf.bean.ShoppingCartBean;
import com.rhg.qf.datebase.AccountDBHelper;
import com.rhg.qf.datebase.AccountDao;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:购物车工具类
 * author：remember
 * time：2016/6/5 13:50
 * email：1013773046@qq.com
 */
public class ShoppingCartUtil {
    /**
     * 选择全部，点下全部按钮，改变所有商品选中状态
     */
    public static boolean selectAll(List<ShoppingCartBean> list, boolean isSelectAll, ImageView ivCheck) {
        isSelectAll = !isSelectAll;
        ShoppingCartUtil.checkItem(isSelectAll, ivCheck);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsGroupSelected(isSelectAll);
            for (int j = 0; j < list.get(i).getGoods().size(); j++) {
                list.get(i).getGoods().get(j).setIsChildSelected(isSelectAll);
            }
        }
        return isSelectAll;
    }

    /**
     * 族内的所有组，是否都被选中，即全选
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllGroup(List<ShoppingCartBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isGroupSelected();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组内所有子选项是否全部被选中
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllChild(List<ShoppingCartBean.Goods> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isChildSelected();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单选一个，需要判断整个组的标志，整个族的标志，是否被全选，取消，则
     * 除了选择全部和选择单个可以单独设置背景色，其他都是通过改变值，然后notify；
     *
     * @param list
     * @param groudPosition
     * @param childPosition
     * @return 是否选择全部
     */
    public static boolean selectOne(List<ShoppingCartBean> list, int groudPosition, int childPosition) {
        boolean isSelectAll;
        boolean isSelectedOne = !(list.get(groudPosition).getGoods().get(childPosition).isChildSelected());
        list.get(groudPosition).getGoods().get(childPosition).setIsChildSelected(isSelectedOne);//单个图标的处理
        boolean isSelectCurrentGroup = isSelectAllChild(list.get(groudPosition).getGoods());
        list.get(groudPosition).setIsGroupSelected(isSelectCurrentGroup);//组图标的处理
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    public static boolean selectGroup(List<ShoppingCartBean> list, int groudPosition) {
        boolean isSelectAll;
        boolean isSelected = !(list.get(groudPosition).isGroupSelected());
        list.get(groudPosition).setIsGroupSelected(isSelected);
        for (int i = 0; i < list.get(groudPosition).getGoods().size(); i++) {
            list.get(groudPosition).getGoods().get(i).setIsChildSelected(isSelected);
        }
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    /**
     * 勾与不勾选中选项
     *
     * @param isSelect 原先状态
     * @param ivCheck
     * @return 是否勾上，之后状态
     */
    public static boolean checkItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.drawable.ic_check_green);
        } else {
            ivCheck.setImageResource(R.drawable.ic_uncheck_green);
        }
        return isSelect;
    }

    /**=====================上面是界面改动部分，下面是数据变化部分=========================*/

    /**
     * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
     *
     * @return 0=选中的商品数量；1=选中的商品总价
     */
    public static String[] getShoppingCount(List<ShoppingCartBean> listGoods) {
        String[] infos = new String[2];
        String selectedCount = "0";
        String selectedMoney = "0";
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getGoods().size(); j++) {
                boolean isSelectd = listGoods.get(i).getGoods().get(j).isChildSelected();
                if (isSelectd) {
                    String price = listGoods.get(i).getGoods().get(j).getPrice();
                    String num = listGoods.get(i).getGoods().get(j).getNumber();
                    selectedMoney = DecimalUtil.add(selectedMoney, DecimalUtil.multiplyWithScale(price, num, 2));
                    selectedCount = DecimalUtil.add(selectedCount, num);
                }
            }
        }
        infos[0] = selectedCount;
        infos[1] = selectedMoney;
        return infos;
    }

    public static ArrayList<PayModel.PayBean> getSelectGoods(List<ShoppingCartBean> listGoods) {
        ArrayList<PayModel.PayBean> payBeen = new ArrayList<>();
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getGoods().size(); j++) {
                boolean isSelectd = listGoods.get(i).getGoods().get(j).isChildSelected();
                if (isSelectd) {
                    PayModel.PayBean _pay = new PayModel.PayBean();
                    _pay.setMerchantName(listGoods.get(i).getMerchantName());
                    ShoppingCartBean.Goods _goods = listGoods.get(i).getGoods().get(j);
                    _pay.setProductName(_goods.getGoodsName());
                    _pay.setChecked(true);
                    _pay.setProductId(_goods.getGoodsID());
                    _pay.setProductNumber(_goods.getNumber());
                    _pay.setProductPic(_goods.getGoodsLogoUrl());
                    _pay.setProductPrice(DecimalUtil.multiplyWithScale(_goods.getPrice(), _goods.getNumber(), 2));
                    payBeen.add(_pay);
                }
            }
        }
        return payBeen;
    }


    public static boolean hasSelectedGoods(List<ShoppingCartBean> listGoods) {
        String count = getShoppingCount(listGoods)[0];
        return !"0".equals(count);
    }


    /**
     * 添加某商品的数量到数据库（非通用部分，都有这个动作，但是到底存什么，未可知）
     *
     * @param foodInfoBean
     */
    public static void addGoodToCart(FoodInfoBean foodInfoBean) {
        AccountDao.getInstance().saveCartInfo(foodInfoBean);
    }

    /**
     * 删除某个商品,即删除其ProductID
     *
     * @param productID 规格ID
     */
    public static void delGood(String merchantId, String productID) {
        AccountDao.getInstance().deleteItemInTableById(AccountDBHelper.Q_SHOPPING_CART_TABLE,
                ShoppingCartBean.KEY_FOOD_ID + " =? and " + ShoppingCartBean.KEY_MERCHANT_ID + " =?", new String[]{productID ,merchantId});
    }

    /**
     * 删除全部商品
     */
    public static void delAllGoods() {
        AccountDao.getInstance().deleteAllItemInTable(AccountDBHelper.Q_SHOPPING_CART_TABLE);
    }

    /**
     * 增减数量，操作通用，数据不通用
     */
    public static void addOrReduceGoodsNum(boolean isPlus, ShoppingCartBean.Goods goods, String merchantId, TextView tvNum) {
        String num;
        if (isPlus) {
            num = DecimalUtil.add(goods.getNumber(), "1");
        } else {
            num = "1".equals(goods.getNumber()) ? "1" : DecimalUtil.subtract(goods.getNumber(), "1");
        }
        String productID = goods.getGoodsID();
        tvNum.setText(num);
        goods.setNumber(num);
        updateGoodsNumber(merchantId, productID, num);
    }

    /**
     * 更新购物车的单个商品数量
     *
     * @param productID
     * @param num
     */
    public static void updateGoodsNumber(String merchantID, String productID, String num) {
        AccountDao.getInstance().updateFoodNum(merchantID, productID, num);
    }

    /**
     * 查询购物车商品总数量
     * <p/>
     * 统一使用该接口，而就行是通过何种方式获取数据，数据库、SP、文件、网络，都可以
     *
     * @return
     */
    public static int getGoodsCount() {
        return AccountDao.getInstance().getTableCountByName(AccountDBHelper.Q_SHOPPING_CART_TABLE);
    }


    /**
     * 获取所有商品ID，用于向服务器请求数据（非通用部分）
     *
     * @return
     */
    public static List<FoodInfoBean> getAllProductID() {
        return AccountDao.getInstance().getCartList();
    }

    /**
     * 由于这次服务端没有保存商品数量，需要此步骤来处理数量（非通用部分）
     */
    public static void updateShopList(List<ShoppingCartBean> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ShoppingCartBean scb = list.get(i);
            if (scb == null) {
                continue;
            }
            List<ShoppingCartBean.Goods> list2 = scb.getGoods();
            if (list2 == null) {
                continue;
            }
            for (int j = 0; j < list2.size(); j++) {
                ShoppingCartBean.Goods goods = list2.get(j);
                if (goods == null) {
                    continue;
                }
                String productID = goods.getProductID();
                String num = AccountDao.getInstance().getNumByFoodID(productID);
                list.get(i).getGoods().get(j).setNumber(num);
            }
        }
    }
}
