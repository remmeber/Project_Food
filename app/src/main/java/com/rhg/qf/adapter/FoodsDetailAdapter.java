package com.rhg.qf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.OrderDetailUrlBean;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 *desc
 *author rhg
 *time 2016/7/10 20:32
 *email 1013773046@qq.com
 */
public class FoodsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_HEADER = 1;
    private final static int TYPE_BODY = 2;
    private List<OrderDetailUrlBean.OrderDetailBean.FoodsBean> foodsBeanList;
    int type;

    public FoodsDetailAdapter(Context context, List<OrderDetailUrlBean.OrderDetailBean.FoodsBean> foodsBeanList) {
        this.foodsBeanList = foodsBeanList;
    }

    @Override
    public int getItemViewType(int position) {
        if ((position != (getItemCount() - 1)) && foodsBeanList.get(position).getNum() == null)
            return TYPE_HEADER;
        return TYPE_BODY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER)
            return new FoodHeaderViewHolder(inflater.inflate(R.layout.item_order_header, parent, false));
        return new FoodListViewHolder(inflater.inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderDetailUrlBean.OrderDetailBean.FoodsBean _data = foodsBeanList.get(position);
        if (getItemViewType(position) == TYPE_HEADER)
            ((FoodHeaderViewHolder) holder).tvMerchantName.setText(_data.getRName());
        else {
            ((FoodListViewHolder) holder).tvFoodName.setText(_data.getFName());
            ((FoodListViewHolder) holder).tvFoodPrice.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getString(R.string.countMoney),
                    _data.getPrice()));
            ((FoodListViewHolder) holder).tvFoodNum.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getString(R.string.countNumber),
                    _data.getNum()));
        }
    }

    @Override
    public int getItemCount() {
        return foodsBeanList == null ? 0 : foodsBeanList.size();/*1是给最后的配送费用*/
    }

    class FoodListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_food_name)
        TextView tvFoodName;
        @Bind(R.id.tv_food_price)
        TextView tvFoodPrice;
        @Bind(R.id.tv_food_num)
        TextView tvFoodNum;

        FoodListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FoodHeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvMerchantName)
        TextView tvMerchantName;

        FoodHeaderViewHolder(View headerView) {
            super(headerView);
            ButterKnife.bind(this, headerView);
        }
    }
}
