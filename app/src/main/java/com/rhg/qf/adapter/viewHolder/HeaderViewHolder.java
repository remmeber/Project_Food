package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;

/**
 * Created by remember on 2016/5/8.
 */
public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public ImageView headerstoreimage;
    public TextView headerstorename;
    public TextView headerdemandmoney;
    public TextView headerdelivermoney;
    public TextView headerdistance;
    public LinearLayout headerlayout;

    public HeaderViewHolder(View itemView, int type) {
        super(itemView);
        headerstoreimage = (ImageView) itemView.findViewById(R.id.shop_header_image);
        headerstorename = (TextView) itemView.findViewById(R.id.headerstorename);
        headerdemandmoney = (TextView) itemView.findViewById(R.id.headerdemandmoney);
        headerdelivermoney = (TextView) itemView.findViewById(R.id.headerdelivermoney);
        headerdistance = (TextView) itemView.findViewById(R.id.headerdistanceText);
        headerlayout = (LinearLayout) itemView.findViewById(R.id.ll_header);
    }
}
