package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.ShopDetailUrlBean;
import com.rhg.qf.utils.ImageUtils;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rhg on 2016/12/28.
 */
public class GoodsDetailViewHolder extends BaseVH<CommonListModel<ShopDetailUrlBean.ShopDetailBean>> {
    @Bind(R.id.iv_goods_detail)
    ImageView ivGoodsDetail;
    @Bind(R.id.tv_goods_detail_name)
    TextView tvGoodsDetailName;
    @Bind(R.id.tv_goods_detail_price)
    TextView tvGoodsDetailPrice;
    @Bind(R.id.tv_goods_detail_sell_num)
    TextView tvGoodsDetailSellNum;
    @Bind(R.id.ll_goods_item)
    LinearLayout llGoodsItem;

    public GoodsDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, final int position, final CommonListModel<ShopDetailUrlBean.ShopDetailBean> shopDetailModel) {
        ShopDetailUrlBean.ShopDetailBean shopDetailBean = shopDetailModel.getEntity().get(position);
        ImageUtils.showImage(shopDetailBean.getPic(), ivGoodsDetail);
        tvGoodsDetailName.setText(shopDetailBean.getName());
        tvGoodsDetailPrice.setText(String.format(InitApplication.getInstance().getResources().getString(R.string.countMoney),
                shopDetailBean.getPrice()));
        tvGoodsDetailSellNum.setText(String.format(Locale.ENGLISH,
                InitApplication.getInstance().getResources().getString(R.string.sellNumber), shopDetailBean.getMonthlySales()));
        llGoodsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null)
                    onClick.onItemClick(llGoodsItem, position, shopDetailModel);
            }
        });
    }
}
