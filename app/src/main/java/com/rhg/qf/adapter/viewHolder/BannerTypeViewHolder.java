package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.rhg.qf.R;
import com.rhg.qf.bean.BannerTypeUrlBean;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.constants.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhg on 2016/12/27.
 */
public class BannerTypeViewHolder extends BaseVH<CommonListModel<BannerTypeUrlBean.BannerEntity>> {

    private final ConvenientBanner convenientBanner;

    public BannerTypeViewHolder(View itemView) {
        super(itemView);
        convenientBanner = (ConvenientBanner) itemView.findViewById(R.id.iv_banner);
        convenientBanner.startTurning(2000);
//        bannerController.setConvenientBanner(convenientBanner);
    }

    @Override
    public void convert(RecyclerView.ViewHolder VH, int position, final CommonListModel<BannerTypeUrlBean.BannerEntity> bannerEntityCommonListModel) {
        List<String> images = new ArrayList<>();
        List<BannerTypeUrlBean.BannerEntity> _bannerEntity = bannerEntityCommonListModel.getEntity();
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
                if (onClick == null)
                    throw new NullPointerException("interface can not be null");
                onClick.onItemClick(convenientBanner, position, bannerEntityCommonListModel);
            }
        });

    }
}
