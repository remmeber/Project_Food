package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.IBaseModel;

/**
 * Created by rhg on 2016/12/26.
 */

public class FooterHolder extends BaseVH<IBaseModel> {
    TextView tv;

    public FooterHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.footerView);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, int position, IBaseModel iBaseModel) {

    }
}
