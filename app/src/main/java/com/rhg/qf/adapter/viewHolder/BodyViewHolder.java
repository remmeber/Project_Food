package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.constants.AppConstants;

/**
 * desc:recycleView的body数据控制器
 * author：remember
 * time：2016/5/28 16:16
 * email：1013773046@qq.com
 */
public class BodyViewHolder extends RecyclerView.ViewHolder {
    public TextView demandMoney;
    public TextView deliverMoney;
    public ImageView sellerImage;//指店铺的图片
    public TextView sellerName;
    public TextView foodType;
    public TextView recommendText;
    public TextView sellerDistance;
    public LinearLayout frameLayout_item;
    public TextView tv_totalMoney;//合计总数
    public TextView tv_orderTime;//订单时间
    public TextView tv_orderTag;//订单号
    public TextView tv_state;//状态{已完成，待付款}
    //这四个在商家页面显示
    private ImageView sellerForward;
    //以下均在主页中显示
    private ImageView homeFoward;//TODO 在商家页面隐藏
    private ImageView sellerIcon;//TODO 在商家页面隐藏
    //以下均在我的订单中显示
    private LinearLayout ly_totalCount;//合计

    public BodyViewHolder(View itemView, int type) {
        super(itemView);
        sellerForward = (ImageView) itemView.findViewById(R.id.imageshowInsellerItem);
        demandMoney = (TextView) itemView.findViewById(R.id.tv_required);
        deliverMoney = (TextView) itemView.findViewById(R.id.tv_money);

        homeFoward = (ImageView) itemView.findViewById(R.id.homeForward);
        sellerIcon = (ImageView) itemView.findViewById(R.id.sellerIcon);
        sellerName = (TextView) itemView.findViewById(R.id.merchantName);
        foodType = (TextView) itemView.findViewById(R.id.foodType);
        recommendText = (TextView) itemView.findViewById(R.id.tv_recommend);
        sellerDistance = (TextView) itemView.findViewById(R.id.tv_distance);
        sellerImage = (ImageView) itemView.findViewById(R.id.sellerImage);
        frameLayout_item = (LinearLayout) itemView.findViewById(R.id.item_layout);

        ly_totalCount = (LinearLayout) itemView.findViewById(R.id.ly_totalCount);
        tv_totalMoney = (TextView) itemView.findViewById(R.id.tv_totalMoney);
        tv_orderTime = (TextView) itemView.findViewById(R.id.tv_orderTime);
        tv_orderTag = (TextView) itemView.findViewById(R.id.tv_orderTag);
        tv_state = (TextView) itemView.findViewById(R.id.tv_state);
        switch (type) {
            case AppConstants.TypeHome:
                sellerIcon.setVisibility(View.VISIBLE);
                foodType.setVisibility(View.VISIBLE);
                recommendText.setVisibility(View.VISIBLE);
                homeFoward.setVisibility(View.VISIBLE);
                sellerDistance.setVisibility(View.VISIBLE);
                tv_state.setVisibility(View.GONE);
                sellerForward.setVisibility(View.GONE);
                tv_orderTime.setVisibility(View.GONE);
                tv_orderTag.setVisibility(View.GONE);
                demandMoney.setVisibility(View.GONE);
                deliverMoney.setVisibility(View.GONE);
                ly_totalCount.setVisibility(View.GONE);
                break;
            case AppConstants.TypeSeller:
                foodType.setVisibility(View.VISIBLE);
                sellerForward.setVisibility(View.VISIBLE);
                demandMoney.setVisibility(View.VISIBLE);
                deliverMoney.setVisibility(View.VISIBLE);
                sellerDistance.setVisibility(View.VISIBLE);
                recommendText.setVisibility(View.GONE);
                homeFoward.setVisibility(View.GONE);
                sellerIcon.setVisibility(View.GONE);
                ly_totalCount.setVisibility(View.GONE);
                tv_orderTime.setVisibility(View.GONE);
                tv_orderTag.setVisibility(View.GONE);
                tv_state.setVisibility(View.GONE);
                break;
            case AppConstants.TypeOrder:
                sellerIcon.setVisibility(View.VISIBLE);
                tv_state.setVisibility(View.VISIBLE);
                sellerForward.setVisibility(View.VISIBLE);
                tv_orderTime.setVisibility(View.VISIBLE);
                tv_orderTag.setVisibility(View.VISIBLE);
                ly_totalCount.setVisibility(View.VISIBLE);
                foodType.setVisibility(View.GONE);
                recommendText.setVisibility(View.GONE);
                homeFoward.setVisibility(View.GONE);
                demandMoney.setVisibility(View.GONE);
                deliverMoney.setVisibility(View.GONE);
                sellerDistance.setVisibility(View.GONE);
                break;
        }
    }
}
