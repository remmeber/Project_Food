package com.rhg.qf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.rhg.qf.R;
import com.rhg.qf.adapter.viewHolder.BannerImageHolder;
import com.rhg.qf.adapter.viewHolder.BodyViewHolder;
import com.rhg.qf.bean.BannerTypeBean;
import com.rhg.qf.bean.BannerTypeUrlBean;
import com.rhg.qf.bean.FavorableFoodUrlBean;
import com.rhg.qf.bean.FavorableTypeModel;
import com.rhg.qf.bean.FooterTypeModel;
import com.rhg.qf.bean.HeaderTypeModel;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.bean.RecommendListTypeModel;
import com.rhg.qf.bean.RecommendTextTypeModel;
import com.rhg.qf.bean.TextTypeBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.RcvItemClickListener;
import com.rhg.qf.utils.BannerController;
import com.rhg.qf.utils.ImageUtils;
import com.rhg.qf.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * desc:复合类型recycleview 适配器
 * author：remember
 * time：2016/5/28 16:21
 * email：1013773046@qq.com
 */
public class RecycleMultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //--------------------------------Define Item Type----------------------------------------------
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_SEARCH = 2;
    private static final int TYPE_FAVORABLE = 3;
    private static final int TYPE_RECOMMEND_TEXT = 4;
    private static final int TYPE_RECOMMEND_LIST = 5;
    private static final int TYPE_FOOTER = 6;
    private BannerController bannerController = new BannerController();
    //----------------------------------------------------------------------------------------------
    //
    private Context context;
    private List<Object> mData;
    private ConvenientBanner<String> convenientBanner;
    //----------------------------------------------------------------------------------------------
    //-------------------------------for banner click callback--------------------------------------
    private OnBannerClickListener onBannerClickListener;
    private OnGridItemClickListener onGridItemClickListener;
    private RcvItemClickListener<MerchantUrlBean.MerchantBean> onItemClickListener;
    private OnSearch onSearch;

    public RecycleMultiTypeAdapter(Context context, List<Object> mData) {
        this.context = context;
        this.mData = mData;
    }

    public void setmData(List<Object> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    //--------------------------------定义类型-------------------------------------------------------
    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        if (position == 1) return TYPE_BANNER;
        if (position == 2) return TYPE_SEARCH;
        if (position == 3) return TYPE_FAVORABLE;
        if (position == 4) return TYPE_RECOMMEND_TEXT;
        if (position == 5 + (((RecommendListTypeModel) mData.get(5)).getRecommendShopBeanEntity() == null ? 0 :
                ((RecommendListTypeModel) mData.get(5)).getRecommendShopBeanEntity().size()))
            return TYPE_FOOTER;
        return TYPE_RECOMMEND_LIST;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderTypeViewHolder(layoutInflater.inflate(R.layout.recycleheader, parent, false));
            case TYPE_BANNER:
                return new BannerTypeViewHolder(layoutInflater.inflate(R.layout.item_banner, parent, false));
            case TYPE_SEARCH:
                return new SearchTypeViewHolder(layoutInflater.inflate(R.layout.search_rcv, parent, false));
            case TYPE_FAVORABLE:
                return new FavorableTypeViewHolder(layoutInflater.inflate(R.layout.grid_type_layout, parent, false), viewType);
            case TYPE_RECOMMEND_TEXT:
                return new RecommendTextTypeViewHolder(layoutInflater.inflate(R.layout.recommend_text_rcv, parent, false));
            case TYPE_RECOMMEND_LIST:
                return /*new RecommendListTypeViewHolder(layoutInflater.inflate(R.layout.recommend_list_rcv, parent, false), viewType);*/
                        new BodyViewHolder(View.inflate(context, R.layout.item_sell_body, null), AppConstants.TypeHome);
            case TYPE_FOOTER:
                return new FooterTypeViewHolder(layoutInflater.inflate(R.layout.rcv_footer_layout, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                bindViewHolderHeader((HeaderTypeViewHolder) holder, (HeaderTypeModel) mData.get(position), position);
                break;
            case TYPE_BANNER:
                bindViewHolderBanner((BannerTypeViewHolder) holder, (BannerTypeBean) mData.get(position));
                break;
            case TYPE_SEARCH:
                bindViewHolderText((SearchTypeViewHolder) holder, (TextTypeBean) mData.get(position));
                break;
            case TYPE_FAVORABLE:
                bindViewHolderFavorable((FavorableTypeViewHolder) holder, (FavorableTypeModel) mData.get(position));
                break;
            case TYPE_RECOMMEND_TEXT:
                bindViewHolderRecommendText((RecommendTextTypeViewHolder) holder, (RecommendTextTypeModel) mData.get(position), position);
                break;
            case TYPE_RECOMMEND_LIST:
                bindViewHolderRecommendList((BodyViewHolder) holder, (RecommendListTypeModel) mData.get(5), position);
                break;
            case TYPE_FOOTER:
                bindViewHolderFooter((FooterTypeViewHolder) holder, (FooterTypeModel) mData.get(6), position);
                break;
        }
    }

    private void bindViewHolderHeader(HeaderTypeViewHolder holder, HeaderTypeModel data, int position) {
       /* holder.button.setText(data.getText());
        holder.button.setBackgroundColor(context.getResources().getColor(data.getColor()));*/
    }

    private void bindViewHolderBanner(BannerTypeViewHolder holder, final BannerTypeBean data) {
        List<String> images = new ArrayList<>();
        List<BannerTypeUrlBean.BannerEntity> _bannerEntity = data.getBannerEntityList();
        int _count = _bannerEntity == null ? 0 : _bannerEntity.size();
        for (int i = 0; i < _count; i++) {
            images.add(_bannerEntity.get(i).getSrc());
        }
        convenientBanner.setPages(new CBViewHolderCreator<BannerImageHolder>() {
            @Override
            public BannerImageHolder createHolder() {
                return new BannerImageHolder();
            }
        }, images)
                .setPageIndicator(AppConstants.IMAGE_INDICTORS);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onBannerClickListener.bannerClick(convenientBanner,position, data.getBannerEntityList().get(position));
            }
        });
    }

    private void bindViewHolderText(SearchTypeViewHolder holder, TextTypeBean data) {
        holder.VipText.setText(data.getTitle());
        holder.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSearch != null)
                    onSearch.search();
            }
        });
    }

    @SuppressWarnings("NewApi")
    private void bindViewHolderFavorable(FavorableTypeViewHolder holder,
                                         final FavorableTypeModel favorableFoodEntity) {
        holder.dpGridViewAdapter.notifyDataSetChanged();
        if (onGridItemClickListener != null) {
            if (!holder.gridView.hasOnClickListeners()) {
                holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onGridItemClickListener.gridItemClick(view, favorableFoodEntity.getFavorableFoodBeen().get(position));
                    }
                });
            }
        }

    }

    private void bindViewHolderRecommendText(RecommendTextTypeViewHolder holder, RecommendTextTypeModel data, int position) {
    }

    private void bindViewHolderRecommendList(final BodyViewHolder holder, RecommendListTypeModel listData, int position) {
//        holder.homeRecycleAdapter.notifyDataSetChanged();
//        holder.homeRecycleAdapter.setOnRcvItemClickListener(data.getOnItemClick());
        final MerchantUrlBean.MerchantBean data = listData.getRecommendShopBeanEntity().get(position - 5);
        holder.sellerName.setText(data.getName());
        ImageUtils.showImage(data.getPic(), holder.sellerImage);
        holder.sellerDistance.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.tvDistance),
                data.getDistance()));
        holder.foodType.setText(data.getStyle());
        holder.recommendText.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.recommendation), data.getReason()));
        if (onItemClickListener != null)
            holder.frameLayout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(holder.frameLayout_item,holder.getAdapterPosition(), data);
                }
            });
    }

    private void bindViewHolderFooter(FooterTypeViewHolder holder, FooterTypeModel data, int position) {
        holder.footerText.setText(context.getResources().getString(R.string.footerText));
    }

    @Override
    public int getItemCount() {
        return (mData == null || mData.isEmpty()) ? 0 : mData.size() - 1 + (((RecommendListTypeModel) mData.get(5)).getRecommendShopBeanEntity() == null ? 0 :
                ((RecommendListTypeModel) mData.get(5)).getRecommendShopBeanEntity().size());
    }

    public void stopBanner() {
        bannerController.stopBanner();
        bannerController.setConvenientBanner(null);
    }

    public void startBanner() {
        bannerController.startBanner(2000);
        bannerController.setConvenientBanner(convenientBanner);
    }

    public void setBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public void setOnGridItemClickListener(OnGridItemClickListener onGridItemClickListener) {
        this.onGridItemClickListener = onGridItemClickListener;
    }

    public void setOnItemClickListener(RcvItemClickListener<MerchantUrlBean.MerchantBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnSearch(OnSearch onSearch) {
        this.onSearch = onSearch;
    }

    public interface OnBannerClickListener {
        void bannerClick(View view,int position, BannerTypeUrlBean.BannerEntity bannerEntity);
    }

    public interface OnGridItemClickListener {
        void gridItemClick(View view, FavorableFoodUrlBean.FavorableFoodEntity favorableFoodEntity);
    }

    public interface OnSearch {
        void search();
    }

    private class HeaderTypeViewHolder extends RecyclerView.ViewHolder {
//        private final Button button;

        public HeaderTypeViewHolder(View itemView) {
            super(itemView);
            /*button = (Button) itemView.findViewById(R.id.headerButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "header is click", Toast.LENGTH_SHORT).show();
                }
            });*/
        }
    }

    private class BannerTypeViewHolder extends RecyclerView.ViewHolder {

        public BannerTypeViewHolder(View itemView) {
            super(itemView);
            convenientBanner = (ConvenientBanner) itemView.findViewById(R.id.iv_banner);
            convenientBanner.startTurning(2000);
            bannerController.setConvenientBanner(convenientBanner);
        }
    }

    private class SearchTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView VipText;
        private TextView searchView;

        public SearchTypeViewHolder(View itemView) {
            super(itemView);
            VipText = (TextView) itemView.findViewById(R.id.VipText);
            searchView = (TextView) itemView.findViewById(R.id.home_search);
        }
    }

    private class FavorableTypeViewHolder extends RecyclerView.ViewHolder {
        private final MyGridView gridView;
        private QFoodGridViewAdapter dpGridViewAdapter;

        public FavorableTypeViewHolder(View itemView, int position) {
            super(itemView);
            gridView = (MyGridView) itemView.findViewById(R.id.gridview);
            gridView.setNumColumns(3);
            dpGridViewAdapter = ((FavorableTypeModel) mData.get(position)).getDpGridViewAdapter();
            gridView.setAdapter(dpGridViewAdapter);
        }
    }
    //----------------------------------------------------------------------------------------------

    private class RecommendTextTypeViewHolder extends RecyclerView.ViewHolder {

        public RecommendTextTypeViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class FooterTypeViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        public FooterTypeViewHolder(View itemView) {
            super(itemView);
            footerText = (TextView) itemView.findViewById(R.id.footerView);

        }
    }


}
