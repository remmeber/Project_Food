package com.rhg.qf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.ShopDetailUrlBean;
import com.rhg.qf.impl.RcvItemClickListener;
import com.rhg.qf.utils.ImageUtils;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * desc:商品详情里面的商品适配器
 * author：remember
 * time：2016/6/22 15:08
 * email：1013773046@qq.com
 */
public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ShopDetailUrlBean.ShopDetailBean> shopDetailBeanList;
    private RcvItemClickListener<ShopDetailUrlBean.ShopDetailBean> onGoodsItemClickListener;

    public GoodsListAdapter(Context context, List<ShopDetailUrlBean.ShopDetailBean> shopDetailBeanList) {
        this.context = context;
        this.shopDetailBeanList = shopDetailBeanList;
    }

    public void setShopDetailBeanList(List<ShopDetailUrlBean.ShopDetailBean> shopDetailBeanList) {
        this.shopDetailBeanList = shopDetailBeanList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View.inflate(context,R.layout.item_goods_layout,parent); TODO 用View.inflate会采用默认的wrap_content布局
        LayoutInflater inflater = LayoutInflater.from(context);
        return new GoodsDetailViewHolder(inflater.inflate(R.layout.item_goods_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsDetailViewHolder goodsDetailViewHolder = (GoodsDetailViewHolder) holder;
        ShopDetailUrlBean.ShopDetailBean shopDetailBean = shopDetailBeanList.get(position);
        bindData(goodsDetailViewHolder, shopDetailBean);
    }

    private void bindData(final GoodsDetailViewHolder goodsDetailViewHolder,
                          final ShopDetailUrlBean.ShopDetailBean shopDetailBean) {
        ImageUtils.showImage(shopDetailBean.getPic(),
                goodsDetailViewHolder.ivGoodsDetail);
        goodsDetailViewHolder.tvGoodsDetailName.setText(shopDetailBean.getName());
        goodsDetailViewHolder.tvGoodsDetailPrice.setText(String.format(context.getResources().getString(R.string.countMoney),
                shopDetailBean.getPrice()));
        goodsDetailViewHolder.tvGoodsDetailSellNum.setText(String.format(Locale.ENGLISH,
                context.getResources().getString(R.string.sellNumber), shopDetailBean.getMonthlySales()));
        goodsDetailViewHolder.llGoodsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoodsItemClickListener != null)
                    onGoodsItemClickListener.onItemClickListener(goodsDetailViewHolder.llGoodsItem,goodsDetailViewHolder.getAdapterPosition(),
                            shopDetailBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopDetailBeanList == null ? 0 : shopDetailBeanList.size();
    }

    public void setOnGoodsItemClickListener(RcvItemClickListener<ShopDetailUrlBean.ShopDetailBean>
                                                    onGoodsItemClickListener) {
        this.onGoodsItemClickListener = onGoodsItemClickListener;
    }

    public class GoodsDetailViewHolder extends RecyclerView.ViewHolder {
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
    }

    /*private class GoodsDetailViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private ImageView goodsImage;
        private TextView goodsName;
        private TextView goodsPrice;
        private TextView goodsSellNum;

        public GoodsDetailViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.ll_goods_item);
            goodsImage = (ImageView) itemView.findViewById(R.id.iv_goods_detail);
            goodsName = (TextView) itemView.findViewById(R.id.tv_goods_detail_name);
            goodsPrice = (TextView) itemView.findViewById(R.id.tv_goods_detail_price);
            goodsSellNum = (TextView) itemView.findViewById(R.id.tv_goods_detail_sell_num);
        }
    }*/
}
