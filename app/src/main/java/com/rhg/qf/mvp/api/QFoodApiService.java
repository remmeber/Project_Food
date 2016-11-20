package com.rhg.qf.mvp.api;

import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.BannerTypeUrlBean;
import com.rhg.qf.bean.BaseBean;
import com.rhg.qf.bean.DeliverInfoBean;
import com.rhg.qf.bean.DeliverOrderUrlBean;
import com.rhg.qf.bean.DeliverStateBean;
import com.rhg.qf.bean.FavorableFoodUrlBean;
import com.rhg.qf.bean.GoodsDetailUrlBean;
import com.rhg.qf.bean.HeadMerchantUrlBean;
import com.rhg.qf.bean.HotFoodUrlBean;
import com.rhg.qf.bean.MerchantInfoDetailUrlBean;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.bean.NewOrderBackBean;
import com.rhg.qf.bean.OrderDetailUrlBean;
import com.rhg.qf.bean.OrderUrlBean;
import com.rhg.qf.bean.ShopDetailUrlBean;
import com.rhg.qf.bean.SignInBackBean;
import com.rhg.qf.bean.TextTypeBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * desc:APP网络请求接口类
 * author：remember
 * time：2016/5/28 16:54
 * email：1013773046@qq.com
 */
public interface QFoodApiService {
    /*首页API*/
    @FormUrlEncoded
    @POST("/Table/Json.php")
    Observable<BannerTypeUrlBean> getBannerUrl(@Field("Table") String headRowPic);

    @GET("json/message.html")
    Observable<TextTypeBean> getMessage();

    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<FavorableFoodUrlBean> getFavorableFood(@Field("Table") String headHot);

    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<MerchantUrlBean> getHomeMerchants(@Field("Table") String headrestaurants,
                                                 @Field("Y") String y,
                                                 @Field("X") String x);

    /*TODO API11 所有店铺head*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<HeadMerchantUrlBean> getHeadMerchant(@Field("Table") String toprestaurant,
                                                    @Field("Y") String y,
                                                    @Field("X") String x);

    /*所有店铺body*/
    @FormUrlEncoded
    @POST("Table/Json.php")
//table : restaurants,order：0.按销量 1.按距离 2.按评分
    Observable<MerchantUrlBean> getBodyMerchants(@Field("Table") String table,
                                                 @Field("Order") int order,
                                                 @Field("Y") String y,
                                                 @Field("X") String x);

    /*TODO API2 店铺食品详情*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    //table:food; merchantId:商家Id号
    Observable<ShopDetailUrlBean> getMerchantFoods(@Field("Table") String food,
                                                   @Field("Restaurant") int merchantId);

    /*TODO API23 店铺信息详情*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    //table:food; merchantId:商家Id号
    Observable<MerchantInfoDetailUrlBean> getMerchantInfo(@Field("Table") String restaurantdetail,
                                                          @Field("ID") String merchantId);


    /*TODO API3 商品详情*/
    @FormUrlEncoded
    @POST("Table/FoodJson.php")
//table:foodmessage Foodid:food id号
    Observable<GoodsDetailUrlBean> getGoodsDetail(@Field("Table") String table,
                                                  @Field("Foodid") int foodId);


    /*TODO API4 用户订单列表 API4*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    //order:foodmessage;userId:用户ID;style:0.全部、1.待付款、2.进行中、3.已完成、、4.已退款
    Observable<OrderUrlBean> getOrders(@Field("Table") String order,
                                       @Field("Client") String userId,
                                       @Field("Style") String style);

    /*TODO API24 订单详情*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<OrderDetailUrlBean> getOrderDetail(@Field("Table") String orderDetail,
                                                  @Field("ID") String orderId);

    /*TODO API5 upload head image*/
    @Multipart
    @POST("Clientpic2.php")
    //return: success error
    Observable<BaseBean> UploadHeadImage(@Part MultipartBody.Part file,
                                         @Part("Client") RequestBody userName,
                                         @Part("Pwd") RequestBody pwd);

    /*get head image*/
    @POST("Pic/ClientPic/{userName}.jpg")
    Observable<String> getHeadImage(@Path("userName") String userName);

    /*TODO API6 user sign in*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<SignInBackBean> userSignIn(@Field("Table") String client,
                                          @Field("Client") String userName,
                                          @Field("Pwd") String pwd);

    /*TODO API14 用户注册*/
    @FormUrlEncoded
    @POST("Table/JsonSQL/AddClient.php")
    Observable<BaseBean> userSignUp(@Field("Name") String openid,
                                    @Field("Pwd") String unionid,
                                    @Field("Pic") String headimageurl,
                                    @Field("Nickname") String nickName);

    /*TODO API12 获取地址*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<AddressUrlBean> getAddress(@Field("Table") String address,
                                          @Field("ID") String userId);

/*    @FormUrlEncoded
    @POST("/Table/Json.php")
    Observable<AddressUrlBean> getDefaultAddress(@Field("Table") String defaultaddress,
                                                 @Field("ID") String userId);*/

    /*TODO API7添加地址*/
    @FormUrlEncoded
    @POST("Table/JsonSQL/AddAddress.php")
    Observable<BaseBean> addAddress(@Field("Client") String userName,
                                    @Field("Pwd") String pwd,
                                    @Field("Name") String name,
                                    @Field("Phone") String phone,
                                    @Field("Address") String address,
                                    @Field("Detail") String detail);

    /*TODO API13 修改地址*/
    @FormUrlEncoded
    @POST("Table/JsonSQL/UpdateAddress.php")
    Observable<BaseBean> updateAddress(@Field("Client") String userName,
                                       @Field("ID") String id,
                                       @Field("Name") String name,
                                       @Field("Phone") String phone,
                                       @Field("Address") String address,
                                       @Field("Pwd") String pwd);

    /*删除地址*/
    @FormUrlEncoded
    @POST("Table/JsonSQL/{AddressChange}.php")/*DeleteAddress*/
    Observable<BaseBean> changeAddress(@Path("AddressChange") String opt,
                                       @Field("ID") String addressId);


    /*
     *TODO API8 desc 厅搜索接口，入口位于主页和所有店铺页
     *author rhg
     *time 2016/7/7 17:12
     *email 1013773046@qq.com
     */
    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<MerchantUrlBean> getRestaurantSearchResult(@Field("Table") String searchRestaurants,
                                                          @Field("Restaurantkey") String searchContent,
                                                          @Field("Order") String style);

    @FormUrlEncoded
    @POST("Table/Json.php")
    /*style:0表示默认 1表示按销量 2表示按距离 3表示按评分*/
    Observable<HotFoodUrlBean> getHotGoods(@Field("Table") String hotFood,
                                           @Field("Order") String style,
                                           @Field("Key") String key);

    /*
     *TODO API10 desc 热销单品搜索，入口位于热销单品页面
     *author rhg
     *time 2016/7/7 17:12
     *email 1013773046@qq.com
     */
    @FormUrlEncoded
    @POST("Table/Json.php")
    /*style:0表示默认 1表示按销量 2表示按距离 3表示按评分*/
    Observable<HotFoodUrlBean> getHotGoodsForSearch(@Field("Table") String hotFood,
                                                    @Field("Hotfoodkey") String searchContent,
                                                    @Field("Order") String order,
                                                    @Field("Y") String y,
                                                    @Field("X") String x);


    /*TODO API19 跑腿员订单详情*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<DeliverOrderUrlBean> getDeliverOrder(@Field("Table") String deliverorder,
                                                    @Field("Deliver") String deliverId);

    /*TODO API20 跑腿员订单配送查询*/
    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<DeliverStateBean> getDeliverState(@Field("Table") String orderStyle,
                                                 @Field("Ordernumber") String orderId);


    @FormUrlEncoded
    @POST("Table/JsonSQL/Orderover.php")
    /*style =0 :请求退单，用于用户；style = 1:完成订单，跑腿员和用户均可
     TODO API15 修改用户、跑腿员订单状态 */
    Observable<BaseBean> modifyOrderState(@Field("ID") String orderId,
                                          @Field("Style") String style);


    /*TODO API 18 生成订单标记为待付款状态*/
    @FormUrlEncoded
    @POST("Table/JsonSQL/AddNewOrder2.php")
    Observable<NewOrderBackBean> createOrder(@Field("Address") String address,
                                             @Field("Client") String client,
                                             @Field("Receiver") String receiver,
                                             @Field("Phone") String phone,
                                             @Field("Price") String price,
                                             @Field("Y") String y,
                                             @Field("X") String x,
                                             @Field("Food") String foodBeen
                                     /*@Body NewOrderBean newOrderBean*/);


    /*TODO API15 修改跑腿员订单的状态 */
    @FormUrlEncoded
    @POST("Table/JsonSQL/{deliverState}.php")/*UPDATE_ORDER_DELIVER、UPDATE_ORDER_PAID、UPdateorderwait*/
    Observable<BaseBean> modifyDeliverOrderState(@Path("deliverState") String deliverState,
                                                 @Field("ID") String orderId);

    /*TODO API21自主点单 */
    @FormUrlEncoded
    @POST("Table/JsonSQL/ClientHope.php")
    Observable<BaseBean> diyOrderFood(@Field("Client") String orderId,
                                      @Field("Message") String content);

    /*TODO API22跑腿员信息补充 */
    @FormUrlEncoded
    @POST("Table/JsonSQL/AddDeliver.php")
    Observable<BaseBean> perfectDeliverInfo(@Field("Name") String name,
                                            @Field("PersonId") String personId,
                                            @Field("Phonenumber") String phoneNum,
                                            @Field("Pwd") String pwd,
                                            @Field("Area") String area,
                                            @Field("ClientID") String clientId);


    @FormUrlEncoded
    @POST("Table/Json.php")
    Observable<DeliverInfoBean> getDeliverInfo(@Field("Table") String deliver,
                                               @Field("Client") String userId);

}
