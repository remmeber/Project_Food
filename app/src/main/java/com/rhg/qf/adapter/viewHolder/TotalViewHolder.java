package com.rhg.qf.adapter.viewHolder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.TotalModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rhg on 2016/12/28.
 */

public class TotalViewHolder extends BaseVH<TotalModel> {


    @Bind(R.id.total_food_money)
    TextView totalFoodMoney;
    @Bind(R.id.total_deliver_money)
    TextView totalDeliverMoney;

    public TotalViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        ForegroundColorSpan red = new ForegroundColorSpan(Color.RED);
        String money = "含跑腿费:￥";
        SpannableStringBuilder deliver_sb = new SpannableStringBuilder(money);
        deliver_sb.setSpan(red, money.indexOf("￥"), money.indexOf("￥") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        totalDeliverMoney.setText(deliver_sb);

        money = "共计:￥";
        SpannableStringBuilder total_sb = new SpannableStringBuilder(money);
        total_sb.setSpan(red, money.indexOf("￥"), money.indexOf("￥") + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        totalFoodMoney.setText(total_sb);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, int position, TotalModel stringCommonListModel) {
        if (stringCommonListModel.getEntity() == null)
            return;

        totalDeliverMoney.append(stringCommonListModel.getEntity().get(TotalModel.DELIVER_FEE));
        totalFoodMoney.append(stringCommonListModel.getEntity().get(TotalModel.ORDER_TOTAL_PRICE));
        /*money = String.format("总需费用:￥%s", stringCommonListModel.getEntity().get(1));
        SpannableStringBuilder total_sb = new SpannableStringBuilder(money);
        total_sb.setSpan(red, money.indexOf("￥"), money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        totalFoodMoney.setText(total_sb);*/

    }
}
