package com.rhg.qf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.OrderDetailUrlBean;

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
    Context context;
    private OrderDetailUrlBean.OrderDetailBean foodsBeanList;
    int type;

    public FoodsDetailAdapter(Context context, OrderDetailUrlBean.OrderDetailBean foodsBeanList) {
        this.context = context;
        this.foodsBeanList = foodsBeanList;
    }

    public void setFoodsBeanList(OrderDetailUrlBean.OrderDetailBean foodsBeanList) {
        this.foodsBeanList = foodsBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if ((position != (getItemCount() - 1)) && foodsBeanList.getFoods().get(position).getNum() == null)
            return TYPE_HEADER;
        return TYPE_BODY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_HEADER)
            return new FoodHeaderViewHolder(inflater.inflate(R.layout.item_order_header, parent, false));
        return new FoodListViewHolder(inflater.inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            ((FoodListViewHolder) holder).tvFoodName.setText("配送费");
            ((FoodListViewHolder) holder).tvFoodPrice.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.countMoney), foodsBeanList.getFee()));
            return;
        }
        OrderDetailUrlBean.OrderDetailBean.FoodsBean _data = foodsBeanList.getFoods().get(position);
        if (getItemViewType(position) == TYPE_HEADER)
            ((FoodHeaderViewHolder) holder).tvMerchantName.setText(_data.getRName());
        else {
            ((FoodListViewHolder) holder).tvFoodName.setText(_data.getFName());
            ((FoodListViewHolder) holder).tvFoodPrice.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.countMoney),
                    _data.getPrice()));
            ((FoodListViewHolder) holder).tvFoodNum.setText(String.format(Locale.ENGLISH, "× %s",
                    _data.getNum()));
        }
    }

    @Override
    public int getItemCount() {
        return foodsBeanList == null ? 0 :
                foodsBeanList.getFoods() == null ? 0 : foodsBeanList.getFoods().size() + 1;/*1是给最后的配送费用*/
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
