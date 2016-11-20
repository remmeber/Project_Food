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
public class FoodsDetailAdapter extends RecyclerView.Adapter<FoodsDetailAdapter.FoodListViewHolder> {

    Context context;
    OrderDetailUrlBean.OrderDetailBean foodsBeanList;

    public FoodsDetailAdapter(Context context, OrderDetailUrlBean.OrderDetailBean foodsBeanList) {
        this.context = context;
        this.foodsBeanList = foodsBeanList;
    }

    public void  setFoodsBeanList(OrderDetailUrlBean.OrderDetailBean foodsBeanList) {
        this.foodsBeanList = foodsBeanList;
        notifyDataSetChanged();
    }

    @Override
    public FoodsDetailAdapter.FoodListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new FoodListViewHolder(inflater.inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(FoodsDetailAdapter.FoodListViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            holder.tvFoodName.setText("配送费");
            holder.tvFoodPrice.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.countMoney), foodsBeanList.getFee()));
            return;
        }
        OrderDetailUrlBean.OrderDetailBean.FoodsBean _data = foodsBeanList.getFoods().get(position);
        holder.tvFoodName.setText(_data.getFName());
        holder.tvFoodPrice.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.countMoney),
                _data.getPrice()));
        holder.tvFoodNum.setText(String.format(Locale.ENGLISH, "× %s",
                _data.getNum()));
    }

    @Override
    public int getItemCount() {
        return foodsBeanList == null ? 0 :
                foodsBeanList.getFoods() == null ? 0 : foodsBeanList.getFoods().size() + 1;
    }

    public class FoodListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_food_name)
        TextView tvFoodName;
        @Bind(R.id.tv_food_price)
        TextView tvFoodPrice;
        @Bind(R.id.tv_food_num)
        TextView tvFoodNum;

        public FoodListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
