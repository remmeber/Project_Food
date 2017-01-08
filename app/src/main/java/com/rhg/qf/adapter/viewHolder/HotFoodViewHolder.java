package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.HotFoodUrlBean;
import com.rhg.qf.utils.ImageUtils;
import com.rhg.qf.widget.MyRatingBar;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 *desc 热销单品页面以及热销搜索viewHolder
 *author rhg
 *time 2016/7/7 21:44
 *email 1013773046@qq.com
 */
public class HotFoodViewHolder extends BaseVH<CommonListModel<HotFoodUrlBean.HotFoodBean>> {
    @Bind(R.id.hot_sell_merchant_name)
    public TextView hotSellMerchantName;
    @Bind(R.id.hot_sell_food_image)
    public ImageView hotSellFoodImage;
    @Bind(R.id.hot_sell_food_name)
    public TextView hotSellFoodName;
    @Bind(R.id.hot_sell_deliver_require)
    public TextView hotSellDeliverRequire;
    @Bind(R.id.hot_sell_deliver_money)
    public TextView hotSellDeliverMoney;
    @Bind(R.id.hot_sell_rating_bar)
    public MyRatingBar hotSellRatingBar;
    @Bind(R.id.hot_sell_deliver_distance)
    public TextView hotSellDeliverDistance;
    @Bind(R.id.hot_sell_total_money)
    public TextView hotSellTotalMoney;
    @Bind(R.id.hot_sell_ll)
    public LinearLayout hotSellLl;

    public HotFoodViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, final int position, final CommonListModel<HotFoodUrlBean.HotFoodBean> hotFoodBeanCommonListModel) {
        if (hotFoodBeanCommonListModel == null)
            return;
        if (hotFoodBeanCommonListModel.getEntity() == null)
            return;
        HotFoodUrlBean.HotFoodBean hotFoodBean = hotFoodBeanCommonListModel.getEntity().get(position);
        hotSellLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null)
                    onClick.onItemClick(hotSellLl, position, hotFoodBeanCommonListModel);
            }
        });
        hotSellMerchantName.setText(hotFoodBean.getRName());
        ImageUtils.showImage(hotFoodBean.getPic(),
                hotSellFoodImage);
        /*hotFoodViewHolder.hotSellFoodImage.setImageDrawable(
                context.getResources().getDrawable(R.drawable.recommend_default_icon_1));*/
        hotSellFoodName.setText(hotFoodBean.getFName());
        hotSellDeliverRequire.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.tvDeliverRequire), hotFoodBean.getDelivery()));
        hotSellDeliverMoney.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.tvDeliverFee), hotFoodBean.getFee()));
        hotSellRatingBar.setStarRating(Float.parseFloat(hotFoodBean.getStars()));
//        hotFoodViewHolder.hotSellDeliverDistance.setText(hotFoodBean.getDistance());
        hotSellTotalMoney.setText(String.format(
                InitApplication.getInstance().getResources().getString(R.string.countMoney), hotFoodBean.getPrice()
        ));
    }

}
