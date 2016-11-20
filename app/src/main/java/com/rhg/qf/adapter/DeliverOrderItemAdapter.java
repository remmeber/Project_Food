package com.rhg.qf.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.DeliverOrderUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.RcvItemClickListener;
import com.rhg.qf.utils.DecimalUtil;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 *desc 跑腿员订单管理适配器
 *author rhg
 *time 2016/7/9 9:58
 *email 1013773046@qq.com
 */
public class DeliverOrderItemAdapter extends RecyclerView.Adapter<DeliverOrderItemAdapter.DeliverOrderViewHolder> {

    Context context;
    List<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeanList;
    private RcvItemClickListener<DeliverOrderUrlBean.DeliverOrderBean> onRcvItemClick;
    private OrderStyleListener onStyleChange;

    public DeliverOrderItemAdapter(Context context, List<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeanList) {
        this.context = context;
        this.deliverOrderBeanList = deliverOrderBeanList;
    }

    public void setDeliverOrderBeanList(List<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeanList) {
        this.deliverOrderBeanList = deliverOrderBeanList;
        notifyDataSetChanged();
    }

    public void updateCertainPosition(List<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeanList, int position) {
        this.deliverOrderBeanList = deliverOrderBeanList;
        notifyItemChanged(position);
    }

    @Override
    public DeliverOrderItemAdapter.DeliverOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeliverOrderViewHolder(View.inflate(context, R.layout.order_manage_item, null));
    }

    @Override
    public void onBindViewHolder(final DeliverOrderItemAdapter.DeliverOrderViewHolder holder, int position) {
        final String _style = deliverOrderBeanList.get(position).getStyle();
        /*if (position % 2 == 0)
            _style = "60";
        else _style = "80";*/
        setStyleAndBackground(holder, _style);
        fillContent(holder, deliverOrderBeanList.get(position));
        holder.rlOrderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRcvItemClick != null)
                    onRcvItemClick.onItemClickListener(holder.rlOrderInfo, holder.getAdapterPosition(),
                            deliverOrderBeanList.get(holder.getAdapterPosition()));
            }
        });
        holder.tvOrderInd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onStyleChange != null)
                    onStyleChange.onStyleChange(_style, holder.getAdapterPosition());
            }
        });
    }

    private void fillContent(DeliverOrderViewHolder holder, DeliverOrderUrlBean.DeliverOrderBean deliverOrderBean) {
        holder.tvOrderMerchantName.setText(deliverOrderBean.getName());
        holder.tvOrderMoney.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.countMoney),
                DecimalUtil.addWithScale(deliverOrderBean.getFee() == null ? "0" : deliverOrderBean.getFee(),
                        deliverOrderBean.getPrice() == null ? "0" : deliverOrderBean.getPrice(), 2)));
        holder.tvOrderNum.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.tvOrderNumber),
                deliverOrderBean.getID()));
    }

    private void setStyleAndBackground(DeliverOrderViewHolder holder, String style) {
        switch (style) {
            /*case AppConstants.DELIVER_ORDER_UNPAID:
                holder.tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderUnpaid));
                holder.tvOrderInd.setBackgroundColor(context.getResources().getColor(R.color.colorRecommend_Red));
                holder.rlOrderInfo.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.virtual_red));
                break;*/
            case AppConstants.DELIVER_ORDER_UNACCEPT:
                holder.tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderUnAccept));
                holder.tvOrderInd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueNormal));
                holder.viewOrderLine.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRecommend_Red));
                holder.rlOrderInfo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.virtual_green));
                break;
            case AppConstants.DELIVER_ORDER_ACCEPT:
                holder.tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderAccept));
                holder.tvOrderInd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRecommend_Red));
                holder.viewOrderLine.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRecommend_Red));
                holder.rlOrderInfo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.virtual_red));
                break;
            case AppConstants.DELIVER_ORDER_DELIVERING:
                holder.tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderDelivering));
                holder.tvOrderInd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueNormal));
                holder.viewOrderLine.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueNormal));
                holder.rlOrderInfo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.virtual_green));
                break;
            case AppConstants.DELIVER_ORDER_COMPLETE:
                holder.tvOrderInd.setText(context.getResources().getString(R.string.deliverOrderFinish));
                holder.tvOrderInd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorInActive));
                holder.viewOrderLine.setBackgroundColor(ContextCompat.getColor(context, R.color.colorInActive));
                holder.rlOrderInfo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.virtual_gray));
                holder.rlOrderInfo.setClickable(false);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return deliverOrderBeanList == null ? 0 : deliverOrderBeanList.size();
    }

    public void setOnRcvItemClick(RcvItemClickListener<DeliverOrderUrlBean.DeliverOrderBean> onRcvItemClick) {
        this.onRcvItemClick = onRcvItemClick;
    }

    public void setOnStyleChange(OrderStyleListener onStyleChange) {
        this.onStyleChange = onStyleChange;
    }

    public interface OrderStyleListener {
        void onStyleChange(String style, int position);
    }

    public class DeliverOrderViewHolder extends RecyclerView.ViewHolder {
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
    }
}
