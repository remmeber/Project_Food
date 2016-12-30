package com.rhg.qf.adapter.viewHolder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.DeliverOrderUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.utils.DecimalUtil;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rhg on 2016/12/28.
 */
public class DeliverOrderViewHolder extends BaseVH<CommonListModel<DeliverOrderUrlBean.DeliverOrderBean>> {
    @Bind(R.id.tv_order_merchant_name)
    TextView tvOrderMerchantName;
    @Bind(R.id.tv_order_money)
    TextView tvOrderMoney;
    @Bind(R.id.tv_order_num)
    TextView tvOrderNum;
    @Bind(R.id.tv_order_distance)
    TextView tvOrderDistance;
    @Bind(R.id.view_order_line)
    View viewOrderLine;
    @Bind(R.id.rl_order_info)
    RelativeLayout rlOrderInfo;
    @Bind(R.id.tv_order_ind)
    TextView tvOrderInd;

    public DeliverOrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, final int position, final CommonListModel<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeanCommonListModel) {
        final String _style = deliverOrderBeanCommonListModel.getEntity().get(position).getStyle();
        setStyleAndBackground(InitApplication.getInstance(), _style);
        fillContent(deliverOrderBeanCommonListModel.getEntity().get(position));
        rlOrderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null)
                    onClick.onItemClick(rlOrderInfo, position, deliverOrderBeanCommonListModel);
            }
        });
        tvOrderInd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null)
                    onClick.onItemClick(tvOrderInd, position, deliverOrderBeanCommonListModel);
            }
        });
    }

    private void fillContent(DeliverOrderUrlBean.DeliverOrderBean deliverOrderBean) {
        tvOrderMerchantName.setText(deliverOrderBean.getName());
        tvOrderMoney.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getResources().getString(R.string.countMoney),
                DecimalUtil.addWithScale(deliverOrderBean.getFee() == null ? "0" : deliverOrderBean.getFee(),
                        deliverOrderBean.getPrice() == null ? "0" : deliverOrderBean.getPrice(), 2)));
        tvOrderNum.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getResources().getString(R.string.tvOrderNumber),
                deliverOrderBean.getID()));
    }

    private void setStyleAndBackground(Context context, String style) {
        switch (style) {
            /*case AppConstants.DELIVER_ORDER_UNPAID:
                holder.tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderUnpaid));
                holder.tvOrderInd.setBackgroundColor(context.getResources().getColor(R.color.colorRecommend_Red));
                holder.rlOrderInfo.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.virtual_red));
                break;*/
            case AppConstants.DELIVER_ORDER_UNACCEPT:
                tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderUnAccept));
                tvOrderInd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorToolbarGreen));
                viewOrderLine.setBackgroundColor(ContextCompat.getColor(context, R.color.colorToolbarGreen));
                rlOrderInfo.setBackgroundResource(R.drawable.virtual_green);
                break;
            case AppConstants.DELIVER_ORDER_ACCEPT:
                tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderAccept));
                tvOrderInd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRecommend_Red));
                viewOrderLine.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRecommend_Red));
                rlOrderInfo.setBackgroundResource(R.drawable.virtual_red);
                break;
            case AppConstants.DELIVER_ORDER_DELIVERING:
                tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderDelivering));
                tvOrderInd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueNormal));
                viewOrderLine.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueNormal));
                rlOrderInfo.setBackgroundResource(R.drawable.virtual_blue);
                break;
            case AppConstants.DELIVER_ORDER_COMPLETE:
                tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderFinish));
                tvOrderInd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorInActive));
                viewOrderLine.setBackgroundColor(ContextCompat.getColor(context, R.color.colorInActive));
                rlOrderInfo.setBackgroundResource(R.drawable.virtual_gray);
                rlOrderInfo.setClickable(false);
                break;
        }
    }
}
