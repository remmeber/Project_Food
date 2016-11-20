package com.rhg.qf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.impl.RcvItemClickListener;

import java.util.List;

/**
 * desc:历史搜索适配器
 * author：remember
 * time：2016/6/18 16:00
 * email：1013773046@qq.com
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<String> searchHistory;
    private RcvItemClickListener<String> onSearchItemClick;

    public SearchHistoryAdapter(Context context, List<String> searchHistory) {
        this.context = context;
        this.searchHistory = searchHistory;
    }

    public void setSearchedHistory(List<String> searchHistory) {
        this.searchHistory = searchHistory;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search_history, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SearchHistoryViewHolder searchHistoryViewHolder = (SearchHistoryViewHolder) holder;
        final int clickPosition = holder.getAdapterPosition();
        searchHistoryViewHolder.tvSearchHistory.setText(searchHistory.get(clickPosition));
        searchHistoryViewHolder.tvSearchHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSearchItemClick != null)
                    onSearchItemClick.onItemClickListener(searchHistoryViewHolder.tvSearchHistory,clickPosition, searchHistory.get(clickPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchHistory == null ? 0 : searchHistory.size();
    }

    public void setOnSearchHistoryClickListener(RcvItemClickListener<String> onSearchItemClick) {
        this.onSearchItemClick = onSearchItemClick;
    }

    private class SearchHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvSearchHistory;

        public SearchHistoryViewHolder(View view) {
            super(view);
            tvSearchHistory = (TextView) view.findViewById(R.id.tv_search_history);
        }
    }
}
