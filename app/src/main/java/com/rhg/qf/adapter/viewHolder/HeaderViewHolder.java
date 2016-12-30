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
 * Created by remember on 2016/5/8.
 */
public class HeaderViewHolder extends BaseVH<CommonListModel<MerchantUrlBean.MerchantBean>> {
    @Bind(R.id.shop_header_image)
    ImageView shopHeaderIcon;
    @Bind(R.id.header_seller_name)
    TextView headerSellerName;
    @Bind(R.id.header_demand_money)
    TextView headerDemandMoney;
    @Bind(R.id.header_deliver_money)
    TextView headerDeliverMoney;
    @Bind(R.id.header_distance_text)
    TextView headerDistanceText;
    @Bind(R.id.ll_header)
    LinearLayout llHeader;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void convert(final RecyclerView.ViewHolder VH, int position, final CommonListModel<MerchantUrlBean.MerchantBean> merchantBeanCommonListModel) {
        if (merchantBeanCommonListModel.getEntity() == null)
            return;
        MerchantUrlBean.MerchantBean data = merchantBeanCommonListModel.getEntity().get(0);

        ImageUtils.showImage(data.getPic(), shopHeaderIcon);
        headerSellerName.setText(data.getName());
        headerDemandMoney.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.tvDeliverRequire), data.getDelivery()));
        headerDeliverMoney.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.tvDeliverFee), data.getFee()));
        headerDistanceText.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.tvDistance), data.getDistance()));
        llHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null)
                    onClick.onItemClick(llHeader, VH.getAdapterPosition(), merchantBeanCommonListModel);
            }
        });
    }
}
