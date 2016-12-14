package com.rhg.qf.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.rhg.qf.R;
import com.rhg.qf.adapter.viewHolder.BodyViewHolder;
import com.rhg.qf.bean.BannerTypeBean;
import com.rhg.qf.bean.BannerTypeUrlBean;
import com.rhg.qf.bean.FavorableFoodUrlBean;
import com.rhg.qf.bean.FavorableTypeModel;
import com.rhg.qf.bean.FooterTypeModel;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.bean.RecommendListTypeModel;
import com.rhg.qf.bean.TextTypeBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.utils.ImageUtils;
import com.rhg.qf.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.rhg.qf.constants.AppConstants.IMAGE_INDICTORS;

/**
 * desc:复合类型recycleview 适配器
 * author：remember
 * time：2016/5/28 16:21
 * email：1013773046@qq.com
 */
public class RecycleMultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //--------------------------------Define Item Type----------------------------------------------
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_IND = 1;
    private static final int TYPE_FAVORABLE = 2;
    private static final int TYPE_RECOMMEND_LIST = 3;
    private static final int TYPE_FOOTER = 4;
    //----------------------------------------------------------------------------------------------
    //
    private Context context;
    private List<Object> mData;
    //----------------------------------------------------------------------------------------------
    //-------------------------------for banner click callback--------------------------------------
/*    private OnBannerClickListener onBannerClickListener;
    private OnGridItemClickListener onGridItemClickListener;
    private RcvItemClickListener<MerchantUrlBean.MerchantBean> onItemClickListener;
    private OnSearch onSearch;*/
    private OnTypeClick onTypeClick;

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
        if (position == 0) return TYPE_BANNER;
        if (position == 1) return TYPE_IND;
        if (position == 2) return TYPE_FAVORABLE;
        if (position == 3 + (((RecommendListTypeModel) mData.get(TYPE_RECOMMEND_LIST)).getRecommendShopBeanEntity() == null ? 0 :
                ((RecommendListTypeModel) mData.get(TYPE_RECOMMEND_LIST)).getRecommendShopBeanEntity().size()))
            return TYPE_FOOTER;
        return TYPE_RECOMMEND_LIST;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_BANNER:
                return new BannerTypeViewHolder(layoutInflater.inflate(R.layout.item_banner, parent, false));
            case TYPE_IND:
                return new IndTypeViewHolder(layoutInflater.inflate(R.layout.person_todayrec_rcv, parent, false));
            case TYPE_FAVORABLE:
                return new FavorableTypeViewHolder(layoutInflater.inflate(R.layout.grid_type_layout, parent, false), viewType);
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
            case TYPE_BANNER:
                bindViewHolderBanner((BannerTypeViewHolder) holder, (BannerTypeBean) mData.get(position));
                break;
            case TYPE_IND:
                bindViewHolderText((IndTypeViewHolder) holder, (TextTypeBean) mData.get(position));
                break;
            case TYPE_FAVORABLE:
                bindViewHolderFavorable((FavorableTypeViewHolder) holder, (FavorableTypeModel) mData.get(position));
                break;
            case TYPE_RECOMMEND_LIST:
                bindViewHolderRecommendList((BodyViewHolder) holder, (RecommendListTypeModel) mData.get(TYPE_RECOMMEND_LIST), position);
                break;
            case TYPE_FOOTER:
                bindViewHolderFooter((FooterTypeViewHolder) holder, (FooterTypeModel) mData.get(TYPE_FOOTER), position);
                break;
        }
    }

    private void bindViewHolderBanner(final BannerTypeViewHolder holder, final BannerTypeBean data) {
        List<String> images = new ArrayList<>();
        List<BannerTypeUrlBean.BannerEntity> _bannerEntity = data.getBannerEntityList();
        int _count = _bannerEntity == null ? 0 : _bannerEntity.size();
        for (int i = 0; i < _count; i++) {
            images.add(_bannerEntity.get(i).getSrc());
        }
        holder.bannerLoopAdapter.setImgSrc(images);
        holder.banner.setOnItemClickListener(new com.jude.rollviewpager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onTypeClick.bannerClick(holder.banner, position, data.getBannerEntityList().get(position));
            }
        });
       /* convenientBanner.setPages(new CBViewHolderCreator<BannerImageHolder>() {
            @Override
            public BannerImageHolder createHolder() {
                return new BannerImageHolder();
            }
        }, images)
                .setPageIndicator(AppConstants.IMAGE_INDICTORS);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (onTypeClick == null)
                    throw new NullPointerException("interface can not be null");
                onTypeClick.bannerClick(convenientBanner, position, data.getBannerEntityList().get(position));
            }
        });*/
    }

    @SuppressLint("NewApi")
    private void bindViewHolderText(IndTypeViewHolder holder, TextTypeBean data) {

        if (!holder.rlPersonOrder.hasOnClickListeners())
            holder.rlPersonOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTypeClick == null)
                        throw new NullPointerException("interface can not be null");
                    onTypeClick.toPersonOrder();
                }
            });
        if (!holder.rlTodayRecommend.hasOnClickListeners())
            holder.rlTodayRecommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTypeClick == null)
                        throw new NullPointerException("interface can not be null");
                    onTypeClick.toRecommend();
                }
            });

    }

    @SuppressLint("NewApi")
    private void bindViewHolderFavorable(FavorableTypeViewHolder holder,
                                         final FavorableTypeModel favorableFoodEntity) {
        holder.dpGridViewAdapter.notifyDataSetChanged();
        if (!holder.gridView.hasOnClickListeners()) {
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onTypeClick == null)
                        throw new NullPointerException("interface can not be null");
                    onTypeClick.gridItemClick(view, favorableFoodEntity.getFavorableFoodBeen().get(position));
                }
            });
        }

    }

    private void bindViewHolderRecommendList(final BodyViewHolder holder, RecommendListTypeModel listData, int position) {
        final MerchantUrlBean.MerchantBean data = listData.getRecommendShopBeanEntity().get(position - TYPE_RECOMMEND_LIST);
        holder.sellerName.setText(data.getName());
        ImageUtils.showImage(data.getPic(), holder.sellerImage);
        holder.sellerDistance.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.tvDistance),
                data.getDistance()));
        holder.foodType.setText(data.getStyle());
        holder.recommendText.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.recommendation), data.getReason()));
        if (onTypeClick == null)
            throw new NullPointerException("interface can not be null");
        holder.frameLayout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTypeClick.onItemClickListener(holder.frameLayout_item, holder.getAdapterPosition(), data);
            }
        });
    }

    private void bindViewHolderFooter(FooterTypeViewHolder holder, FooterTypeModel data, int position) {
        holder.footerText.setText(context.getResources().getString(R.string.footerText));
    }

    @Override
    public int getItemCount() {
        return (mData == null || mData.isEmpty()) ? 0 : mData.size() - 1 + (((RecommendListTypeModel) mData.get(TYPE_RECOMMEND_LIST))
                .getRecommendShopBeanEntity() == null ? 0 :
                ((RecommendListTypeModel) mData.get(TYPE_RECOMMEND_LIST)).getRecommendShopBeanEntity().size());
    }

/*    public void stopBanner() {
        bannerController.stopBanner();
        bannerController.setConvenientBanner(null);
    }

    public void startBanner() {
        bannerController.startBanner();
        bannerController.setConvenientBanner(banner);
    }*/

    public void setOnTypeClick(OnTypeClick onTypeClick) {
        this.onTypeClick = onTypeClick;
    }

    public interface OnTypeClick {

        void bannerClick(View view, int position, BannerTypeUrlBean.BannerEntity bannerEntity);

        void gridItemClick(View view, FavorableFoodUrlBean.FavorableFoodEntity favorableFoodEntity);

        void onItemClickListener(View view, int position, MerchantUrlBean.MerchantBean data);

        void toPersonOrder();

        void toRecommend();

    }

    private class BannerTypeViewHolder extends RecyclerView.ViewHolder {
        public RollPagerView banner;
        public BannerLoopAdapter bannerLoopAdapter;

        public BannerTypeViewHolder(View itemView) {
            super(itemView);
            banner = (RollPagerView) itemView.findViewById(R.id.iv_banner);
            bannerLoopAdapter = new BannerLoopAdapter(banner);
            banner.setAdapter(bannerLoopAdapter);
            banner.setHintView(new IconHintView(context, IMAGE_INDICTORS[1], IMAGE_INDICTORS[0]));
        }
    }

    class IndTypeViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlPersonOrder;
        RelativeLayout rlTodayRecommend;

        IndTypeViewHolder(View itemView) {
            super(itemView);
            rlPersonOrder = (RelativeLayout) itemView.findViewById(R.id.rl_person_order);
            rlTodayRecommend = (RelativeLayout) itemView.findViewById(R.id.rl_today_recommend);
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

    private class FooterTypeViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        public FooterTypeViewHolder(View itemView) {
            super(itemView);
            footerText = (TextView) itemView.findViewById(R.id.footerView);

        }
    }


}
