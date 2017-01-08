package com.rhg.qf.adapter.viewHolder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.PayModel;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rhg on 2016/12/28.
 */
public class PayItemViewHolder extends BaseVH<CommonListModel<PayModel.PayBean>> {
    @Bind(R.id.tv_pay_title)
    TextView tvPayTitle;
    @Bind(R.id.iv_item_pay)
    ImageView ivItemPay;
    @Bind(R.id.iv_pay_image)
    ImageView ivPayImage;
    @Bind(R.id.tv_pay_item_name)
    TextView tvPayItemName;
    @Bind(R.id.tv_pay_money)
    TextView tvPayMoney;
    @Bind(R.id.tv_pay_item_number)
    TextView tvPayItemNumber;


    PayItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, final int position, final CommonListModel<PayModel.PayBean> payModel) {
        if (payModel.getEntity() == null)
            return;
        PayModel.PayBean payBean = payModel.getEntity().get(position);
        if (payBean.getMerchantName() != null)
            tvPayTitle.setText(payBean.getMerchantName());
        else tvPayTitle.setText(payBean.getProductName());
        tvPayItemName.setText(payBean.getProductName());
        tvPayItemNumber.setText(String.format(InitApplication.getInstance().getString(R.string.countNumber), payBean.getProductNumber()));
        if (payBean.isChecked())
            ivItemPay.setImageDrawable(ContextCompat.getDrawable(InitApplication.getInstance(), R.drawable.ic_check_blue));
        else
            ivItemPay.setImageDrawable(ContextCompat.getDrawable(InitApplication.getInstance(), R.drawable.ic_uncheck_blue));
        ivItemPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null)
                    onClick.onItemClick(ivItemPay, position, payModel);
            }
        });
        tvPayMoney.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getResources().getString(R.string.countMoney),
                payBean.getProductPrice()));
        ImageLoader.getInstance().displayImage(payBean.getProductPic(), ivPayImage);

    }
}
