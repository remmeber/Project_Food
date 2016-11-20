package com.rhg.qf.constants;

import android.os.Environment;

import com.rhg.qf.R;

/**
 * desc:APP的一些常量
 * author：remember
 * time：2016/5/28 16:40
 * email：1013773046@qq.com
 */
public class AppConstants {
    public static boolean DEBUG = false;
    public static final String f_Path = Environment.getExternalStorageDirectory() + "/QFood";

    public static final int[] IMAGE_INDICTORS = new int[]{R.drawable.ic_page_indicator,
            R.drawable.ic_page_indicator_focused};
    public static final String[] SHOP_DETAIL_TITLES = new String[]{"菜品", "店铺详情"};
    public static final String[] SELL_TITLES = new String[]{"按销量", "按距离", "按评分"};
    public static final String[] ORDER_TITLES = new String[]{"待付款", "进行中", "已完成", "已退款"};
    public static final String[] HOT_SELL_TITLES = new String[]{"综合", "距离", "销量", "评分"};
    //---------------------------店铺复用------------------------------------------------------------
    public static final int TypeHome = 0;
    public static final int TypeSeller = 1;
    public static final int TypeOrder = 2;
    //---------------------------所有店铺页面中的header和body-----------------------------------------
    public static final int TypeHeader = 1;
    public static final int TypeBody = 2;

    /*SP KEY*/
    public static final String SP_LOCATION = "location";
    public static final String SP_LATITUDE = "latitude";
    public static final String SP_LONGITUDE = "longitude";
    public static final String SP_HEAD_IMAGE = "sp_head_image";
    public static final String SP_USER_ID = "user_id";
    public static final String SP_DELIVER_ID = "deliver_id";
    public static final String SP_USER_NAME = "user_name";
    public static final String SP_PASSWORD = "password";
    public static final String SP_NICKNAME = "nickname";
    /*SP KEY*/
    /**
     * 页面调起/销毁标志
     */
    public static final int BACK_WITH_DELETE = 4;
    public static final int BACK_WITHOUT_DATA = 5;
    public static final int BACK_WITH_UPDATE = 6;
    public static final int BACK_WITH_ADD = 7;

    /*signin*/
    public static final String USERNAME_WX = "nickname";
    public static final String UNIONID_WX = "unionid";
    public static final String OPENID_WX = "openid";
    public static final String PROFILE_IMAGE_WX = "headimgurl";
    /*signin*/


    /**
     * Intent/Bundle 传递Bean的KEY
     */
    public static final String KEY_INTENT_BEAN = "bean";
    public static final String KEY_INTENT_FRAGMENT_ID = "fragment_id";
    public static final String KEY_MERCHANT_NAME = "merchant_name";
    public static final String KEY_PRODUCT_NAME = "product_name";
    public static final String KEY_MERCHANT_ID = "merchant_id";
    public static final String KEY_PRODUCT_ID = "product_id";
    public static final String KEY_ORDER_ID = "order_id";
    public static final String KEY_MERCHANT_LOGO = "merchant_logo";
    public static final String KEY_PRODUCT_PRICE = "product_price";
    public static final String KEY_OR_SP_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_NOTE = "note";
    public static final String KEY_DELETE = "delete";
    public static final String KEY_ORDER_TAG = "order_tag";
    public static final String KEY_SEARCH_INDEX = "search_index";
    public static final String KEY_SEARCH_TAG = "search_tag";
    public static final String KEY_PARCELABLE = "parcelable";
    public static final int KEY_RESTAURANT_SEARCH = 30;// TODO: 首页和商家页面搜索餐厅的KEY
    public static final int KEY_HOTFOOD_SEARCH = 31;// TODO: 热销商品搜索的KEY


    /*for headImage*/
    public static final int CODE_GALLERY_REQUEST = 10;//TODO:From Local
    public static final int CODE_GALLERY_REQUEST_KITKAT = 20;//TODO:From Local
    public static final int CODE_CAMERA_REQUEST = 11;//TODO: From Camera
    public static final int CODE_RESULT_REQUEST = 12;//TODO:From Crop

    /*Address*/
    public static final String NAME_FOR_ADDRESS = "address_name";
    public static final String PHONE_FOR_ADDRESS = "address_phone";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_DETAIL = "address_detail";
    public static final String ADDRESS_ID = "address_id";
    public static final String ADDRESS_DEFAULT = "address_default";

    /*Merchant*/
    public static final int MERCHANT_SELLNUMBER = 0;
    public static final int MERCHANT_DISTANCE = 1;
    public static final int MERCHANT_RATE = 2;
    /*Order*/
    public static final int ORDER_ALL = 0;
    public static final int USER_ORDER_UNPAID = 1;
    public static final int USER_ORDER_DELIVERING = 2;
    public static final int USER_ORDER_COMPLETE = 3;
    public static final int USER_ORDER_DRAWBACK = 4;

    public static final String DELIVER_ORDER_UNPAID = "20"/*"待付款"*/;
    public static final String DELIVER_ORDER_UNACCEPT = "40"/*"待接单"*/;
    public static final String DELIVER_ORDER_ACCEPT = "60"/*"已接单"*/;
    public static final String DELIVER_ORDER_DELIVERING = "80"/*"配送中"*/;
    public static final String DELIVER_ORDER_COMPLETE = "100"/*"已完成"*/;

    public static final String ORDER_WITHDRAW = "0";/*用户订单退款*/
    public static final String ORDER_FINISH = "1";/*用户订单完成*/
    public static final String ORDER_DELIVERING = "2";/*用户订单修改为配送中*/

    /*HotFood*/
    public static final int OVERALL = 0;
    public static final int SELLNUMBER = 1;
    public static final int DISTANCE = 2;
    public static final int RATE = 3;

    /*后台交互信息*/
    public static final String TABLE_CLIENT = "client";
    public static final String TABLE_FOODMESSAGE = "foodmessage";
    public static final String TABLE_DEFAULT_ADDRESS = "defaultaddress";
    public static final String TABLE_FOOD = "food";
    public static final String RESTAURANTS = "restaurants";
    public static final String TOP_RESTAURANTS = "toprestaurant";
    public static final String HOME_RESTAURANTS = "headrestaurants";
    public static final String SEARCH_RESTAURANTS = "searchrestaurants";
    public static final String SEARCH_HOTFOOD = "searchhotfood";
    public static final String HOT_FOOD = "hotfood";
    public static final String TABLE_ORDER = "order";
    public static final String ADDRESS_TABLE = "address";
    public static final String DELIVER_ORDER = "deliverorder";
    public static final String DELIVER = "deliver";
    public static final String MERCHANT_INFO = "restaurantdetail";
    public static final String ORDER_DETAIL = "orderdetail";
    public static final String ORDER_STYLE = "orderstyle";
    public static final String HEAD_HOT = "headhot";
    public static final String HEAD_ROW = "headRowPic";
    public static final String DELETE_ADDRESS = "DeleteAddress";
    public static final String CHOOSE_DEFAULT = "ChooseDefault";

    public static final String UPDATE_ORDER_DELIVER = "UPdateorderdeliver";//修改为配送中
    public static final String UPDATE_ORDER_PAID = "UPdateorderPaid";//修改为等待接单
    public static final String UPDATE_ORDER_WAIT = "UPdateorderwait";//修改已经接单接口

    public static final String CUSTOMER_SERVER = "deliciouscs";


}
