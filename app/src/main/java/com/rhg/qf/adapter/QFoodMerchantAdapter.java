package com.rhg.qf.adapter;

import android.content.Context;
import android.view.View;

import com.rhg.qf.R;
import com.rhg.qf.adapter.viewHolder.BodyViewHolder;
import com.rhg.qf.adapter.viewHolder.HeaderViewHolder;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.utils.ImageUtils;

import java.util.List;
import java.util.Locale;

/**
 * desc:
 * author：remember
 * time：2016/6/6 14:14
 * email：1013773046@qq.com
 */
public class QFoodMerchantAdapter extends RecycleAbstractAdapter<MerchantUrlBean.MerchantBean> {

    Context context;

    public QFoodMerchantAdapter(Context context, List<MerchantUrlBean.MerchantBean> mData) {
        super(context, mData);
        this.context = context;
    }


    @Override
    public boolean getHasHead() {
        return true;
    }

    @Override
    public int getDisplayType() {
        return AppConstants.TypeSeller;
    }

    @Override
    protected int getLayoutResId(int viewtype) {
        if (viewtype == AppConstants.TypeHeader)
            return R.layout.all_store_header_cardview;
        return R.layout.item_sell_layout;
    }

    @Override
    public void bindHeadData(final HeaderViewHolder holder, MerchantUrlBean.MerchantBean data, int type) {
        ImageUtils.showImage(data.getPic(), holder.headerstoreimage);
        holder.headerstorename.setText(data.getName());
        holder.headerdemandmoney.setText(String.format(Locale.ENGLISH,
                context.getResources().getString(R.string.tvDeliverRequire), data.getDelivery()));
        holder.headerdelivermoney.setText(String.format(Locale.ENGLISH,
                context.getResources().getString(R.string.tvDeliverFee), data.getFee()));
        holder.headerdistance.setText(String.format(Locale.ENGLISH,
                context.getResources().getString(R.string.tvDistance), data.getDistance()));
        holder.headerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnRcvItemClickListener() != null)
                    getOnRcvItemClickListener().onItemClickListener(holder.headerlayout,holder.getAdapterPosition(), null);
            }
        });
    }

    @Override
    protected void bindBodyData(final BodyViewHolder holder, MerchantUrlBean.MerchantBean data, int type) {
        holder.sellerName.setText(data.getName());
        ImageUtils.showImage(data.getPic(), holder.sellerImage);
        holder.demandMoney.setText(String.format(Locale.ENGLISH,
                context.getResources().getString(R.string.tvDeliverRequire), data.getDelivery()));
        holder.foodType.setText(data.getStyle());
        holder.deliverMoney.setText(String.format(Locale.ENGLISH,
                context.getResources().getString(R.string.tvDeliverFee), data.getFee()));
        holder.sellerDistance.setText(String.format(Locale.ENGLISH,
                context.getResources().getString(R.string.tvDistance), data.getDistance()));
        holder.frameLayout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnRcvItemClickListener() != null)
                    getOnRcvItemClickListener().onItemClickListener(holder.frameLayout_item,holder.getAdapterPosition(), null);
            }
        });

    }
}
