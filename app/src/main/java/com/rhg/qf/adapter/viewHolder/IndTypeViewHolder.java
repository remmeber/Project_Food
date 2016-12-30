package com.rhg.qf.adapter.viewHolder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.rhg.qf.R;
import com.rhg.qf.bean.CommonListModel;

/**
 * Created by rhg on 2016/12/27.
 */

public class IndTypeViewHolder extends BaseVH<CommonListModel<String>> {
    RelativeLayout rlPersonOrder;
    RelativeLayout rlTodayRecommend;

    public IndTypeViewHolder(View itemView) {
        super(itemView);
        rlPersonOrder = (RelativeLayout) itemView.findViewById(R.id.rl_person_order);
        rlTodayRecommend = (RelativeLayout) itemView.findViewById(R.id.rl_today_recommend);
    }

    @SuppressLint("NewApi")
    @Override
    public void convert(RecyclerView.ViewHolder VH, int position, final CommonListModel<String> stringCommonListModel) {
        if (!rlPersonOrder.hasOnClickListeners())
            rlPersonOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClick == null)
                        throw new NullPointerException("interface can not be null");
                    onClick.onItemClick(v, v.getId(), null);
                }
            });
        if (!rlTodayRecommend.hasOnClickListeners())
            rlTodayRecommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClick == null)
                        throw new NullPointerException("interface can not be null");
                    onClick.onItemClick(v, v.getId(), null);
                }
            });
    }

}
