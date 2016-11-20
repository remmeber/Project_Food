package com.rhg.qf.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rhg.qf.R;
import com.rhg.qf.bean.PayModel;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * desc:
 * author：remember
 * time：2016/7/11 0:09
 * email：1013773046@qq.com
 */
public class PayItemAdapter extends RecyclerView.Adapter<PayItemAdapter.PayItemViewHolder> {
    Context context;
    List<PayModel.PayBean> payList;
    PayItemClickListener onPayItemClick;


    public PayItemAdapter(Context context, List<PayModel.PayBean> payList) {
        this.context = context;
        this.payList = payList;
    }

    public void setPayList(List<PayModel.PayBean> payList) {
        this.payList = payList;
        notifyDataSetChanged();
    }

    @Override
    public PayItemAdapter.PayItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        return new PayItemViewHolder(inflate.inflate(R.layout.item_pay, parent, false));
    }

    @Override
    public void onBindViewHolder(final PayItemAdapter.PayItemViewHolder holder, int position) {
        if (payList.get(position).getMerchantName() != null)
            holder.tvPayTitle.setText(payList.get(position).getMerchantName());
        else holder.tvPayTitle.setText(payList.get(position).getProductName());
        if (payList.get(position).isChecked())
            holder.ivItemPay.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check_green));
        else
            holder.ivItemPay.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_uncheck_green));
        holder.ivItemPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPayItemClick != null)
                    onPayItemClick.onPayItemClick(holder.getAdapterPosition());
            }
        });
//        holder.rlPayCheck.setOnClickListener(this);
        holder.lyTotalCount.setVisibility(View.VISIBLE);
     /*   String _totalPrice;
        if ("0".equals(payList.get(position).getProductNumber()))
            _totalPrice = payList.get(position).getProductPrice();
        else
            _totalPrice = *//*Integer.valueOf(payList.get(position).getProductPrice()) *
                    Integer.valueOf(payList.get(position).getProductNumber())*//*
                    DecimalUtil.multiplyWithScale(,
                            payList.get(position).getProductNumber(),
                            2);*/
        holder.tvTotalMoney.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.countMoney),
                payList.get(position).getProductPrice()));
        ImageLoader.getInstance().displayImage(payList.get(position).getProductPic(), holder.ivPayImage);
        holder.tvDeliverFee.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.deliverFee),
                payList.get(position).getDeliverFee() == null | "".equals(payList.get(position).getDeliverFee()) ? "0.00" : payList.get(position).getDeliverFee()));
    }

    @Override
    public int getItemCount() {
        return payList == null ? 0 : payList.size();
    }

    public void setOnPayItemClick(PayItemClickListener onPayItemClick) {
        this.onPayItemClick = onPayItemClick;
    }

    public interface PayItemClickListener {
        void onPayItemClick(int position);
    }

    public class PayItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_pay_title)
        TextView tvPayTitle;
        @Bind(R.id.iv_item_pay)
        ImageView ivItemPay;
        @Bind(R.id.iv_pay_image)
        ImageView ivPayImage;
        @Bind(R.id.tv_totalMoney)
        TextView tvTotalMoney;
        @Bind(R.id.ly_totalCount)
        LinearLayout lyTotalCount;
        @Bind(R.id.item_deliver_fee)
        TextView tvDeliverFee;

        PayItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
