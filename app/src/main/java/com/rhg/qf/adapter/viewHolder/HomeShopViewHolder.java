package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.utils.ImageUtils;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * desc:recycleView的body数据控制器
 * author：remember
 * time：2016/5/28 16:16
 * email：1013773046@qq.com
 */
public class HomeShopViewHolder extends BaseVH<CommonListModel<MerchantUrlBean.MerchantBean>> {

    @Bind(R.id.home_seller_name)
    TextView homeSellerName;
    @Bind(R.id.home_seller_image)
    ImageView homeSellerImage;
    @Bind(R.id.home_food_type)
    TextView homeFoodType;
    @Bind(R.id.tv_home_recommend)
    TextView tvHomeRecommend;
    @Bind(R.id.tv_home_distance)
    TextView tvHomeDistance;
    @Bind(R.id.item_home_layout)
    LinearLayout itemHomeLayout;

    public HomeShopViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void convert(final RecyclerView.ViewHolder VH, final int position, final CommonListModel<MerchantUrlBean.MerchantBean> merchantBeanCommonListModel) {
        if (merchantBeanCommonListModel == null)
            return;
        if (merchantBeanCommonListModel.getEntity() == null)
            return;
        final MerchantUrlBean.MerchantBean data = merchantBeanCommonListModel.getEntity().get(position);
        homeSellerName.setText(data.getName());
        ImageUtils.showImage(data.getPic(), homeSellerImage);
        tvHomeDistance.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getString(R.string.tvDistance),
                data.getDistance()));
        homeFoodType.setText(data.getStyle());
        tvHomeRecommend.setText(String.format(Locale.ENGLISH, InitApplication.getInstance().getResources().getString(R.string.recommendation), data.getReason()));
        if (onClick == null)
            throw new NullPointerException("interface can not be null");
        itemHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(itemHomeLayout, position, merchantBeanCommonListModel);
            }
        });
    }
}
