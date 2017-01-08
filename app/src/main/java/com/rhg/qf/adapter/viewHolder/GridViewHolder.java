package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodGridViewAdapter;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.FavorableFoodUrlBean;
import com.rhg.qf.widget.MyGridView;

/**
 * Created by rhg on 2016/12/26.
 */

public class GridViewHolder extends BaseVH<CommonListModel<FavorableFoodUrlBean.FavorableFoodEntity>> {

    private final MyGridView gridView;
    private QFoodGridViewAdapter dpGridViewAdapter;

    public GridViewHolder(View itemView) {
        super(itemView);
        gridView = (MyGridView) itemView.findViewById(R.id.my_grid);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, int position, final CommonListModel<FavorableFoodUrlBean.FavorableFoodEntity> favorableFoodEntityCommonListModel) {

        if (dpGridViewAdapter == null) {
            gridView.setNumColumns(3);
            dpGridViewAdapter = new QFoodGridViewAdapter(gridView.getContext(), R.layout.item_grid_rcv);
            gridView.setAdapter(dpGridViewAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onClick != null)
                        onClick.onItemClick(view, position, favorableFoodEntityCommonListModel);
                }
            });
        }
        dpGridViewAdapter.setList(favorableFoodEntityCommonListModel.getEntity());
    }

}
