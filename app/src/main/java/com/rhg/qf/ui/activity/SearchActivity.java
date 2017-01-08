package com.rhg.qf.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.MainAdapter;
import com.rhg.qf.adapter.SearchHistoryAdapter;
import com.rhg.qf.adapter.viewHolder.AllShopsViewHolder;
import com.rhg.qf.adapter.viewHolder.HotFoodViewHolder;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.HotFoodUrlBean;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.OnItemClickListener;
import com.rhg.qf.mvp.presenter.HotFoodSearchPresenter;
import com.rhg.qf.mvp.presenter.RestaurantSearchPresenter;
import com.rhg.qf.utils.SearchHistoryUtil;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.RecycleViewDivider;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * desc:搜索页面
 * author：remember
 * time：2016/6/18 13:10
 * email：1013773046@qq.com
 */
public class SearchActivity extends BaseAppcompactActivity implements OnItemClickListener<IBaseModel> {

    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;
    @Bind(R.id.tv_history_result)
    TextView tvHistoryResult;
    @Bind(R.id.tv_search_result)
    TextView tvSearchResult;
    @Bind(R.id.historyResultsRcv)
    RecyclerView historyResultsRcv;
    @Bind(R.id.itemResultsRcv)
    RecyclerView itemResultsRcv;

    SearchHistoryAdapter searchHistoryAdapter;
    MainAdapter<IBaseModel> hotFoodAdapter;
    MainAdapter<IBaseModel> searchMerchantAdapter;
    CommonListModel<MerchantUrlBean.MerchantBean> merchantBeanList;
    CommonListModel<HotFoodUrlBean.HotFoodBean> hotFoodBeanList;
    RestaurantSearchPresenter restaurantSearchPresenter;
    HotFoodSearchPresenter hotFoodSearchPresenter;
    boolean isShow;
    private List<String> searchHistoryData;
    private int searchTag;
    private int searchIndex;
    private OnItemClickListener itemClick = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position, Object item) {
            if (item instanceof String) {
                isShow = false;
                searchEt.setText((String) item);
                doSearch((String) item);
                searchEt.setCursorVisible(false);
                historyResultsRcv.setVisibility(View.GONE);
                tvHistoryResult.setVisibility(View.GONE);
                itemResultsRcv.setVisibility(View.VISIBLE);
                tvSearchResult.setVisibility(View.VISIBLE);
                return;
            }
        }

        @Override
        public void onItemLongClick(View view, int position, Object item) {

        }
    };

    public SearchActivity() {
//        searchHistoryData = new ArrayList<>();
    }

    @Override
    public void dataReceive(Intent intent) {
        searchTag = intent.getIntExtra(AppConstants.KEY_SEARCH_TAG, -1);
        searchIndex = intent.getIntExtra(AppConstants.KEY_SEARCH_INDEX, -1);
    }

    @Override
    public void loadingData() {
        searchHistoryData = SearchHistoryUtil.getAllHistory(/*this*/);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.search_layout;
    }

    @Override
    protected void initData() {
        flTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.chevron_left_black));
        RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,
                SizeUtil.dip2px(1), ContextCompat.getColor(this, R.color.colorInActive));

        itemResultsRcv.setHasFixedSize(false);
        itemResultsRcv.setLayoutManager(new LinearLayoutManager(this));
        itemResultsRcv.addItemDecoration(divider);

        historyResultsRcv.setLayoutManager(new LinearLayoutManager(this));
        historyResultsRcv.setHasFixedSize(false);
        divider.setLeftAndRightPadding(SizeUtil.dip2px(16), 0);
        historyResultsRcv.addItemDecoration(divider);

        if (searchTag == AppConstants.KEY_RESTAURANT_SEARCH) {
            merchantBeanList = new CommonListModel<>();
            searchMerchantAdapter = new MainAdapter<>(
                    new InflateModel(new Class[]{AllShopsViewHolder.class, View.class}, new Object[]{R.layout.item_sell_shop}),
                    merchantBeanList,
                    this);
            itemResultsRcv.setAdapter(searchMerchantAdapter);
        } else {
            hotFoodBeanList = new CommonListModel<>();
            hotFoodAdapter = new MainAdapter<>(
                    new InflateModel(new Class[]{HotFoodViewHolder.class, View.class}, new Object[]{R.layout.item_hot_sell}),
                    hotFoodBeanList,
                    this);
            itemResultsRcv.setAdapter(hotFoodAdapter);
        }
        searchHistoryAdapter = new SearchHistoryAdapter(this, searchHistoryData);
        searchHistoryAdapter.setOnSearchHistoryClickListener(itemClick);

        historyResultsRcv.setAdapter(searchHistoryAdapter);
        searchEt.setVisibility(View.VISIBLE);
        searchEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (searchEt.getCompoundDrawables()[2] == null)
                    return false;
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return true;
                }
                if (event.getRawX() > searchEt.getWidth() -
                        searchEt.getCompoundDrawables()[2].getBounds().width()) {
                    if (TextUtils.isEmpty(searchEt.getText().toString())) {
                        ToastHelper.getInstance().displayToastWithQuickClose("搜索内容为空");
                        return true;
                    }
                    if (searchHistoryData.size() == 0) {
                        SearchHistoryUtil.insertSearchHistory(/*SearchActivity.this,*/ searchEt.getText().toString().trim());
                        searchHistoryData.add(searchEt.getText().toString().trim());
                    }
                        /*切换到内容搜索*/
                    searchEt.setCursorVisible(false);
                    doSearch(searchEt.getText().toString());
                    return true;
                } else {
                    searchEt.setCursorVisible(true);
                    if (TextUtils.isEmpty(searchEt.getText())) {
                        tvHistoryResult.setVisibility(View.VISIBLE);/* 隐藏历史搜索textView*/
                        tvSearchResult.setVisibility(View.GONE);/* 显示内容搜索textView*/
                        historyResultsRcv.setVisibility(View.VISIBLE);/*隐藏历史搜索recycleView*/
                        itemResultsRcv.setVisibility(View.GONE);/* 显示内容搜索recycleView*/
                    }
                }
                return false;
            }
        });
        /*清空搜索历史*/
        tvHistoryResult.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (tvHistoryResult.getCompoundDrawables()[2] == null)
                    return false;
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return true;
                }
                if (event.getRawX() > tvHistoryResult.getWidth()
                        - tvHistoryResult.getCompoundDrawables()[2].getBounds().width()
                        - tvHistoryResult.getPaddingRight()) {
                    SearchHistoryUtil.deleteAllHistory(/*SearchActivity.this*/);

                    searchHistoryAdapter.setSearchedHistory(null);
                    return true;
                }
                return false;
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!isShow) {
                    /*切换到历史搜索*/
                tvHistoryResult.setVisibility(View.VISIBLE);/* 隐藏历史搜索textView*/
                tvSearchResult.setVisibility(View.GONE);/* 显示内容搜索textView*/
                historyResultsRcv.setVisibility(View.VISIBLE);/*隐藏历史搜索recycleView*/
                itemResultsRcv.setVisibility(View.GONE);/* 显示内容搜索recycleView*/
//                }
                searchHistoryData.clear();
                if (s.toString().trim().length() != 0) {
                    searchHistoryData = SearchHistoryUtil.getHistoryByName(/*SearchActivity.this,*/ s.toString().trim());
                } else {
                    searchHistoryData = SearchHistoryUtil.getAllHistory(/*SearchActivity.this*/);
                }
                searchHistoryAdapter.setSearchedHistory(searchHistoryData);
            }
        });
    }

    /*
     *desc 搜索业务
     *author rhg
     *time 2016/7/6 21:29
     *email 1013773046@qq.com
     */
    private void doSearch(String s) {
        switch (searchTag) {
            case AppConstants.KEY_RESTAURANT_SEARCH:
                if (restaurantSearchPresenter == null)
                    restaurantSearchPresenter = new RestaurantSearchPresenter(this);
                restaurantSearchPresenter.getSearchRestaurant(AppConstants.SEARCH_RESTAURANTS, s, searchIndex);
                break;
            case AppConstants.KEY_HOTFOOD_SEARCH:
                if (hotFoodSearchPresenter == null)
                    hotFoodSearchPresenter = new HotFoodSearchPresenter(this);
                hotFoodSearchPresenter.getSearchHotFood(AppConstants.SEARCH_HOTFOOD, s, searchIndex);
                break;
        }

//        searchMerchantAdapter.setmData(null);
    }

    @Override
    public void showSuccess(Object s) {
        if (s instanceof IBaseModel) {
            if (((IBaseModel) s).getEntity() == null)
                return;
            if (((IBaseModel) s).getEntity().get(0) instanceof MerchantUrlBean.MerchantBean) {
                merchantBeanList.setRecommendShopBeanEntity(((IBaseModel) s).getEntity());
                searchMerchantAdapter.notifyDataSetChanged();
            } else {
                hotFoodBeanList.setRecommendShopBeanEntity(((IBaseModel) s).getEntity());
                hotFoodAdapter.notifyDataSetChanged();
            }
        }
        tvHistoryResult.setVisibility(View.GONE);/* 隐藏历史搜索textView*/
        tvSearchResult.setVisibility(View.VISIBLE);/* 显示内容搜索textView*/
        historyResultsRcv.setVisibility(View.GONE);/* 隐藏历史搜索recycleView*/
        itemResultsRcv.setVisibility(View.VISIBLE);/* 显示内容搜索recycleView*/
    }

    @Override
    public void showError(Object s) {
    }

    @OnClick({R.id.tb_left_iv, R.id.tb_right_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tb_left_iv:
                finish();
                break;
            case R.id.tb_right_iv:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        restaurantSearchPresenter = null;
        hotFoodSearchPresenter = null;
        searchMerchantAdapter = null;
        hotFoodAdapter = null;
        itemClick = null;
        searchHistoryData.clear();
        super.onDestroy();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(View view, int position, IBaseModel item) {
        Intent intent = new Intent();
        //noinspection unchecked
        if (item.getEntity().get(position) instanceof MerchantUrlBean.MerchantBean) {
            MerchantUrlBean.MerchantBean data = (MerchantUrlBean.MerchantBean) item.getEntity().get(position);
            intent.setClass(SearchActivity.this, ShopDetailActivity.class);
            intent.putExtra(AppConstants.KEY_MERCHANT_ID, data.getID());
            intent.putExtra(AppConstants.KEY_MERCHANT_NAME, data.getName());
            intent.putExtra(AppConstants.KEY_MERCHANT_LOGO, data.getPic());
        } else {
            HotFoodUrlBean.HotFoodBean data = (HotFoodUrlBean.HotFoodBean) item.getEntity().get(position);
            intent.setClass(SearchActivity.this, GoodsDetailActivity.class);
            intent.putExtra(AppConstants.KEY_PRODUCT_ID, data.getID());
            intent.putExtra(AppConstants.KEY_MERCHANT_ID, data.getRId());
            intent.putExtra(AppConstants.KEY_MERCHANT_NAME, data.getRName());
        }
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(SearchActivity.this).toBundle());
    }

    @Override
    public void onItemLongClick(View view, int position, IBaseModel item) {

    }
}
