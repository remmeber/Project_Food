package com.rhg.qf.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodGridViewAdapter;
import com.rhg.qf.adapter.RecycleMultiTypeAdapter;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.BannerTypeBean;
import com.rhg.qf.bean.BannerTypeUrlBean;
import com.rhg.qf.bean.FavorableFoodUrlBean;
import com.rhg.qf.bean.FavorableTypeModel;
import com.rhg.qf.bean.FooterTypeModel;
import com.rhg.qf.bean.HeaderTypeModel;
import com.rhg.qf.bean.HomeBean;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.bean.RecommendListTypeModel;
import com.rhg.qf.bean.RecommendTextTypeModel;
import com.rhg.qf.bean.TextTypeBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.RcvItemClickListener;
import com.rhg.qf.locationservice.LocationService;
import com.rhg.qf.locationservice.MyLocationListener;
import com.rhg.qf.mvp.presenter.HomePresenter;
import com.rhg.qf.runtimepermissions.PermissionsManager;
import com.rhg.qf.runtimepermissions.PermissionsResultAction;
import com.rhg.qf.ui.activity.HotFoodActivity;
import com.rhg.qf.ui.activity.PersonalOrderActivity;
import com.rhg.qf.ui.activity.SearchActivity;
import com.rhg.qf.ui.activity.ShopDetailActivity;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * desc:主页
 * author：remember
 * time：2016/5/28 16:44
 * email：1013773046@qq.com
 */
public class HomeFragment extends BaseFragment implements RecycleMultiTypeAdapter.OnBannerClickListener,
        RecycleMultiTypeAdapter.OnGridItemClickListener,
        RecycleMultiTypeAdapter.OnSearch,
        RcvItemClickListener<MerchantUrlBean.MerchantBean>,
        View.OnClickListener {

    FavorableTypeModel favorableTypeModel;
    BannerTypeBean bannerTypeBean;
    TextTypeBean textTypeBean;
    RecommendListTypeModel recommendListTypeModel;
    RecycleMultiTypeAdapter recycleMultiTypeAdapter;

    HomePresenter homePresenter;
    MyLocationListener myLocationListener;

    List<Object> mData;
    boolean isLocated;
    boolean firstLoc;

    @Bind(R.id.home_recycle)
    RecyclerView home_rcv;
    @Bind(R.id.home_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {
        myLocationListener = new MyLocationListener(this);
        favorableTypeModel = new FavorableTypeModel();
        bannerTypeBean = new BannerTypeBean();
        textTypeBean = new TextTypeBean();
        recommendListTypeModel = new RecommendListTypeModel();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.home_fm_layout;
    }


    @Override
    protected void initView(View view) {
    }

    @Override
    public void loadData() {
        if (getUserVisibleHint()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE},
                        new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                startLoc();
                            }

                            @Override
                            public void onDenied(String permission) {

                            }
                        });
            } else
                startLoc();
        }
    }

    @Override
    public void onDeny(final String permission) {
        Snackbar.make(view, "授权失败，将影响您的体验", Snackbar.LENGTH_LONG).setAction("重新授权", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(getActivity(),
                        new String[]{permission}, null);
            }
        }).show();
    }

    @Override
    public void onGrant() {
        startLoc();
        firstLoc = true;
    }

    @Override
    protected void refresh() {
        //firstLoc 必须在startLoc方法调用后置位，在6.0以上的系统中，必须授权后再调用startLoc方法，
        //reStartLocation也必须在startLoc方法调用过一次后才能调用。
        if (firstLoc && !isLocated) {
            reStartLocation();
        }
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        recycleMultiTypeAdapter = new RecycleMultiTypeAdapter(getContext(), mData);
        recycleMultiTypeAdapter.setBannerClickListener(this);
        recycleMultiTypeAdapter.setOnGridItemClickListener(this);
        recycleMultiTypeAdapter.setOnItemClickListener(this);
        recycleMultiTypeAdapter.setOnSearch(this);
        initList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        home_rcv.setLayoutManager(linearLayoutManager);
        home_rcv.setHasFixedSize(false);
        home_rcv.setAdapter(recycleMultiTypeAdapter);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if ("".equals(AccountUtil.getInstance().getLatitude())) {
                    reStartLocation();
                    return;
                }
                homePresenter.getHomeData(AppConstants.HOME_RESTAURANTS);
            }
        });
    }

    @Override
    public LocationService GetMapService() {
//        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        return InitApplication.getInstance().locationService;
    }

    @Override
    public void getLocation(LocationService locationService, MyLocationListener mLocationListener) {
    }

    @Override
    public MyLocationListener getLocationListener() {
        return myLocationListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        recycleMultiTypeAdapter.startBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        recycleMultiTypeAdapter.stopBanner();
    }

    @Override
    protected void showFailed() {
    }


    @Override
    public void showSuccess(Object o) {
        HomeBean _homeBean = (HomeBean) o;

        bannerTypeBean.setBannerEntityList(_homeBean.getBannerEntityList());
        favorableTypeModel.setFavorableFoodBeen(_homeBean.getFavorableFoodEntityList());
        recommendListTypeModel.setRecommendShopBeanEntity(_homeBean.getRecommendShopBeanEntityList());
        textTypeBean.setTitle(_homeBean.getTextTypeBean().getTitle());
        recycleMultiTypeAdapter.notifyDataSetChanged();
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }


    /*定位显示*/
    @Override
    public void showLocSuccess(String s) {
        isLocated = true;
        AccountUtil.getInstance().setLocation(s);
        if (homePresenter == null)
            homePresenter = new HomePresenter(this);
        homePresenter.getHomeData(AppConstants.HOME_RESTAURANTS);
    }

    @Override
    public void showLocFailed(String s) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        ToastHelper.getInstance()._toast(s);
    }

    private void initList() {
        mData.add(new HeaderTypeModel("Header", R.color.cardview_shadow_start_color));
        mData.add(bannerTypeBean);
        mData.add(textTypeBean);
        favorableTypeModel.setDpGridViewAdapter(new QFoodGridViewAdapter(getContext(),
                R.layout.item_grid_rcv));
        mData.add(favorableTypeModel);
        mData.add(new RecommendTextTypeModel());
        mData.add(recommendListTypeModel);
        mData.add(new FooterTypeModel("FooterType", R.color.colorPrimaryDark));
        recycleMultiTypeAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.tv_center})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                /*if (NetUtil.isConnected(getContext()))
                    reStartLocation();
                else ToastHelper.getInstance()._toast("请检查网络");*/
                break;
            case R.id.tv_center:
               /* if (!AccountUtil.getInstance().hasAccount()) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }*/
                startActivity(new Intent(getContext(), PersonalOrderActivity.class));
//                doSearch();
                break;
            case R.id.iv_right:
             /*   if (!AccountUtil.getInstance().hasAccount()) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }
                startActivity(new Intent(getContext(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, AppConstants.CUSTOMER_SERVER));*/
                break;
        }
    }

    private void doSearch() {
        Intent _intent = new Intent(getActivity(), SearchActivity.class);
        _intent.putExtra(AppConstants.KEY_SEARCH_TAG, AppConstants.KEY_RESTAURANT_SEARCH);
        _intent.putExtra(AppConstants.KEY_SEARCH_INDEX, 0);
        startActivity(_intent);
    }

    @Override
    public void bannerClick(View view, int position, BannerTypeUrlBean.BannerEntity bannerEntity) {
        Intent intent = new Intent(getContext(), ShopDetailActivity.class);
        intent.putExtra(AppConstants.KEY_MERCHANT_ID, bannerEntity.getID());
        intent.putExtra(AppConstants.KEY_MERCHANT_LOGO, bannerEntity.getSrc());
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @Override
    public void gridItemClick(View view, FavorableFoodUrlBean.FavorableFoodEntity favorableFoodEntity) {
        Intent intent = new Intent(getContext(), HotFoodActivity.class);
        intent.putExtra(AppConstants.KEY_PRODUCT_NAME, favorableFoodEntity.getTitle());
        startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
    }

    @Override
    public void onItemClickListener(View view, int position, MerchantUrlBean.MerchantBean item) {
        Intent intent = new Intent(getContext(), ShopDetailActivity.class);
        intent.putExtra(AppConstants.KEY_MERCHANT_ID, item.getID());
        intent.putExtra(AppConstants.KEY_MERCHANT_NAME, item.getName());
        intent.putExtra(AppConstants.KEY_MERCHANT_LOGO, item.getPic());
        startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
    }


    @Override
    public void search() {
        doSearch();
    }
}
