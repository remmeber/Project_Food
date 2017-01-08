package com.rhg.qf.adapter.viewHolder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.CommonListModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rhg on 2016/12/28.
 */
public class AddressManageViewHolder extends BaseVH<CommonListModel<AddressUrlBean.AddressBean>> {


    @Bind(R.id.checkImage)
    ImageView checkImage;
    @Bind(R.id.tv_address_receiver)
    TextView tvAddressReceiver;
    @Bind(R.id.tv_address_phone)
    TextView tvAddressPhone;
    @Bind(R.id.tv_address_content)
    TextView tvAddressContent;
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.holder)
    LinearLayout holder;

    public AddressManageViewHolder(View inflateView) {
        super(inflateView);
        ButterKnife.bind(this, inflateView);
    }


    @Override
    public void convert(RecyclerView.ViewHolder VH, final int position, final CommonListModel<AddressUrlBean.AddressBean> addressModel) {
        AddressUrlBean.AddressBean addressBean = addressModel.getEntity().get(position);
        tvAddressReceiver.setText(addressBean.getName());
        tvAddressPhone.setText(addressBean.getPhone());
        String _str = addressBean.getAddress().concat(addressBean.getDetail());
        tvAddressContent.setText(_str);
        String defaultAddress = addressBean.getDefault();
        rlAddress.setTag(position);
        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    onClick.onItemClick(rlAddress, position, addressModel);
                }
            }
        });
        rlAddress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClick != null) {
                    onClick.onItemLongClick(rlAddress, position, addressModel);
                }
                return true;
            }
        });
        setImage(defaultAddress, checkImage);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    onClick.onItemClick(holder, position, addressModel);
                }
            }
        });
    }

    private void setImage(String isChecked, ImageView ivCheck) {
        if ("1".equals(isChecked)) {
            ivCheck.setImageDrawable(ContextCompat.getDrawable(InitApplication.getInstance(), R.drawable.ic_check_blue));
        } else
            ivCheck.setImageDrawable(ContextCompat.getDrawable(InitApplication.getInstance(), R.drawable.ic_uncheck_blue));

    }
}
