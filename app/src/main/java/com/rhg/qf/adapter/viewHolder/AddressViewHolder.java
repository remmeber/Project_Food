package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.BaseAddressModel;
import com.rhg.qf.bean.CommonListModel;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rhg on 2016/12/28.
 */
public class AddressViewHolder extends BaseVH<CommonListModel<String>> {

    @Bind(R.id.rl_address_info)
    RelativeLayout rlAddressInfo;
    @Bind(R.id.tv_receiver)
    TextView tvReceiver;
    @Bind(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @Bind(R.id.tv_receiver_address)
    TextView tvReceiverAddress;
    @Bind(R.id.iv_edit_right)
    ImageView ivEditRight;

    public AddressViewHolder(View inflateView) {
        super(inflateView);
        ButterKnife.bind(this, inflateView);
        ivEditRight.setVisibility(View.GONE);
    }


    @Override
    public void convert(RecyclerView.ViewHolder VH, final int position, final CommonListModel<String> addressModel) {
        if (addressModel.getEntity() == null) {
            return;
        }
        tvReceiver.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getString(R.string.tvReceiver),
                addressModel.getEntity().get(BaseAddressModel.ADDRESS_RECEIVER)));
        tvReceiverPhone.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getString(R.string.tvContactPhone),
                addressModel.getEntity().get(BaseAddressModel.ADDRESS_PHONE)));
        tvReceiverAddress.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getString(R.string.tvReceiveAddress),
                addressModel.getEntity().get(BaseAddressModel.ADDRESS_CONTENT).concat(
                        addressModel.getEntity().get(BaseAddressModel.ADDRESS_DETAIL)
                )));
        rlAddressInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    onClick.onItemClick(v, position, addressModel);
                }
            }
        });
    }
}
