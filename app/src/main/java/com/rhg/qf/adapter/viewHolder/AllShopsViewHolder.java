package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.utils.ImageUtils;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by rhg on 2016/12/28.
 */

public class AllShopsViewHolder extends BaseVH<CommonListModel<MerchantUrlBean.MerchantBean>> {


    @Bind(R.id.shop_seller_name)
    TextView shopSellerName;
    @Bind(R.id.shop_seller_image)
    ImageView shopSellerImage;
    @Bind(R.id.shop_food_type)
    TextView shopFoodType;
    @Bind(R.id.tv_shop_distance)
    TextView tvShopDistance;
    @Bind(R.id.tv_shop_required)
    TextView tvShopRequired;
    @Bind(R.id.tv_shop_money)
    TextView tvShopMoney;
    @Bind(R.id.item_shop_layout)
    LinearLayout itemShopLayout;

    public AllShopsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, final int position, final CommonListModel<MerchantUrlBean.MerchantBean> commonListModel) {
        MerchantUrlBean.MerchantBean data = commonListModel.getEntity().get(position);
        shopSellerName.setText(data.getName());
        ImageUtils.showImage(data.getPic(), shopSellerImage);
        tvShopRequired.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.tvDeliverRequire), data.getDelivery()));
        shopFoodType.setText(data.getStyle());
        tvShopMoney.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.tvDeliverFee), data.getFee()));
        tvShopDistance.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.tvDistance), data.getDistance()));
        itemShopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null)
                    onClick.onItemClick(itemShopLayout, position, commonListModel);
            }
        });
    }
}
