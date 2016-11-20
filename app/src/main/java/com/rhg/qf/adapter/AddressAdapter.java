package com.rhg.qf.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.impl.DeleteItemListener;

import java.util.List;

/**
 * desc:
 * author：remember
 * time：2016/6/29 15:39
 * email：1013773046@qq.com
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<AddressUrlBean.AddressBean> addressBeanList;
    private DeleteItemListener mItemListener;

    public AddressAdapter(Context content, List<AddressUrlBean.AddressBean> addressBeanList) {
        this.context = content;
        this.addressBeanList = addressBeanList;
    }

    public void setAddressBeanList(List<AddressUrlBean.AddressBean> addressBeanList) {
        this.addressBeanList = addressBeanList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_address_content, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddressViewHolder _holder = (AddressViewHolder) holder;
        AddressUrlBean.AddressBean addressBean = addressBeanList.get(position);
        bindData(_holder, addressBean, position);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void bindData(final AddressViewHolder holder, AddressUrlBean.AddressBean addressBean,
                          int position) {
        holder.tvReceiver.setText(addressBean.getName());
        holder.tvPhone.setText(addressBean.getPhone());
        String _str = addressBean.getAddress().concat(addressBean.getDetail());
        holder.tvAddress.setText(_str);
        String defaultAddress = addressBean.getDefault();
        holder.rlAddress.setTag(holder.getAdapterPosition());
        holder.rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
        holder.rlAddress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onLongClick(holder.getAdapterPosition());
                }
                return true;
            }
        });
        setImage(defaultAddress, holder.ivCheck);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    Log.i("RHG", "ONCLICK.....");
                    mItemListener.onDelete(holder.getAdapterPosition());
                }
            }
        });
        /*if (holder.rlAddress.hasOnClickListeners())
            return;
        holder.rlAddress.setOnClickListener(this);*/
    }

    private void setImage(String isChecked, ImageView ivCheck) {
        if ("1".equals(isChecked)) {
            ivCheck.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check_green));
        } else
            ivCheck.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_uncheck_green));

    }

    @Override
    public int getItemCount() {
        return addressBeanList == null ? 0 : addressBeanList.size();
    }

    public void setItemListener(DeleteItemListener mItemListener) {
        this.mItemListener = mItemListener;
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlAddress;
        public ImageView ivCheck;
        public TextView tvReceiver;
        public TextView tvPhone;
        public TextView tvAddress;
        public LinearLayout delete;

        public AddressViewHolder(View inflateView) {
            super(inflateView);
            rlAddress = (RelativeLayout) inflateView.findViewById(R.id.rl_address);
            ivCheck = (ImageView) inflateView.findViewById(R.id.checkImage);
            tvReceiver = (TextView) inflateView.findViewById(R.id.tv_address_receiver);
            tvPhone = (TextView) inflateView.findViewById(R.id.tv_address_phone);
            tvAddress = (TextView) inflateView.findViewById(R.id.tv_address_content);
            delete = (LinearLayout) inflateView.findViewById(R.id.holder);
        }

    }
}
