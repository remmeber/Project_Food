package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.OrderUrlBean;
import com.rhg.qf.utils.DecimalUtil;
import com.rhg.qf.utils.ImageUtils;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rhg on 2016/12/28.
 */

public class OrderViewHolder extends BaseVH<CommonListModel<OrderUrlBean.OrderBean>> {
    @Bind(R.id.order_merchant_name)
    TextView orderMerchantName;
    @Bind(R.id.tv_order_state)
    TextView tvOrderState;
    @Bind(R.id.order_seller_image)
    ImageView orderSellerImage;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.tv_order_tag)
    TextView tvOrderTag;
    @Bind(R.id.tv_totalMoney)
    TextView tvTotalMoney;
    @Bind(R.id.item_order_layout)
    LinearLayout itemOrderLayout;

    public OrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, final int position, final CommonListModel<OrderUrlBean.OrderBean> orderBeanCommonListModel) {
        OrderUrlBean.OrderBean data = orderBeanCommonListModel.getEntity().get(position);
        orderMerchantName.setText(data.getRName());
        tvOrderState.setText(data.getStyle());
        tvOrderTime.setText(data.getOtime());
        tvOrderTag.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getResources().getString(R.string.tvOrderNumber),
                data.getID()));
        tvTotalMoney.setText(String.format(InitApplication.getInstance().getResources().getString(R.string.countMoney),
                DecimalUtil.addWithScale(data.getPrice(), data.getFee(), 2)));
        ImageUtils.showImage(data.getPic(), orderSellerImage);
        itemOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null)
                    onClick.onItemClick(itemOrderLayout, position, orderBeanCommonListModel);
            }
        });
        itemOrderLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClick != null)
                    onClick.onItemLongClick(itemOrderLayout, position, orderBeanCommonListModel);
                return true;
            }
        });
    }
}
