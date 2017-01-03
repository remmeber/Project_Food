package com.rhg.qf.ui.fragment;

import android.Manifest;
import android.content.Context;
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
import com.rhg.qf.adapter.MainAdapter;
import com.rhg.qf.adapter.WrapperAdapter;
import com.rhg.qf.adapter.viewHolder.BannerTypeViewHolder;
import com.rhg.qf.adapter.viewHolder.FooterHolder;
import com.rhg.qf.adapter.viewHolder.GridViewHolder;
import com.rhg.qf.adapter.viewHolder.HomeShopViewHolder;
import com.rhg.qf.adapter.viewHolder.IndTypeViewHolder;
import com.rhg.qf.bean.BannerTypeUrlBean;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.FavorableFoodUrlBean;
import com.rhg.qf.bean.HomeBean;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.OnItemClickListener;
import com.rhg.qf.locationservice.MyLocationListener;
import com.rhg.qf.mvp.presenter.HomePresenter;
import com.rhg.qf.mvp.presenter.contact.HomeContact;
import com.rhg.qf.runtimepermissions.PermissionsManager;
import com.rhg.qf.runtimepermissions.PermissionsResultAction;
import com.rhg.qf.ui.activity.HotFoodActivity;
import com.rhg.qf.ui.activity.PersonalOrderActivity;
import com.rhg.qf.ui.activity.RecommendActivity;
import com.rhg.qf.ui.activity.SearchActivity;
import com.rhg.qf.ui.activity.ShopDetailActivity;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.RecycleViewDivider;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * desc:主页
 * author：remember
 * time：2016/5/28 16:44
 * email：1013773046@qq.com
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements OnItemClickListener<IBaseModel>, HomeContact.View<HomeBean> {
    @Bind(R.id.home_recycle)
    RecyclerView home_rcv;
    @Bind(R.id.home_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    CommonListModel<BannerTypeUrlBean.BannerEntity> bannerModel;
    CommonListModel<FavorableFoodUrlBean.FavorableFoodEntity> favorableTypeModel;
    CommonListModel<MerchantUrlBean.MerchantBean> commonListModel;


    WrapperAdapter<IBaseModel> recycleMultiTypeAdapter;

    MyLocationListener myLocationListener;


    boolean isLocated;
    boolean firstLoc;

    public HomeFragment() {
        myLocationListener = new MyLocationListener(this);
        favorableTypeModel = new CommonListModel<>();
        commonListModel = new CommonListModel<>();
        bannerModel = new CommonListModel<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (presenter == null) {
            presenter = new HomePresenter();
        }
        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.detachView();
        }
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
                                HomeFragment.this.onGrant();
                            }

                            @Override
                            public void onDenied(String permission) {

                            }
                        });
            } else {
                swipeRefreshLayout.setRefreshing(true);
                startLoc();
            }
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
        swipeRefreshLayout.setRefreshing(true);
        startLoc();
        firstLoc = true;
    }

    @Override
    protected void refresh() {
        //firstLoc 必须在startLoc方法调用后置位，在6.0以上的系统中，必须授权后再调用startLoc方法，
        //reStartLocation也必须在startLoc方法调用过一次后才能调用。
//        startLoc();
        if ("".equals(AccountUtil.getInstance().getLatitude())) {
            isLocated = false;
        }
        if (firstLoc && !isLocated) {
            startLoc();
        } else {//如果定位过了，直接可以进行数据获取
            presenter.getHomeData(AppConstants.HOME_RESTAURANTS);
        }
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        home_rcv.setLayoutManager(linearLayoutManager);
        home_rcv.setHasFixedSize(false);
        home_rcv.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.HORIZONTAL, SizeUtil.dip2px(2),
                ContextCompat.getColor(getContext(), R.color.colorBackground)));

        MainAdapter<IBaseModel> mainAdapter = new MainAdapter<>(
                new InflateModel(new Class<?>[]{HomeShopViewHolder.class, View.class}, new Object[]{R.layout.item_sell_home}),
                commonListModel,
                this);
        recycleMultiTypeAdapter = new WrapperAdapter<>(mainAdapter, this);
        recycleMultiTypeAdapter.addHeaderViews(new InflateModel(new Class[]{BannerTypeViewHolder.class, View.class}, new Object[]{R.layout.item_banner}), bannerModel);
        recycleMultiTypeAdapter.addHeaderViews(new InflateModel(new Class[]{IndTypeViewHolder.class, View.class}, new Object[]{R.layout.person_todayrec_rcv}));
        recycleMultiTypeAdapter.addHeaderViews(new InflateModel(new Class[]{GridViewHolder.class, View.class}, new Object[]{R.layout.grid_type_layout}), favorableTypeModel);
        recycleMultiTypeAdapter.addFooterViews(new InflateModel(new Class[]{FooterHolder.class, View.class}, new Object[]{R.layout.rcv_footer_layout}));
        home_rcv.setAdapter(recycleMultiTypeAdapter);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
//                reStartLocation();
            }
        });
    }

    /*定位显示*/
    @Override
    public void showLocSuccess(String s) {
        isLocated = true;
        AccountUtil.getInstance().setLocation(s);
        presenter.getHomeData(AppConstants.HOME_RESTAURANTS);
    }

    @Override
    public void showLocFailed(String s) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        ToastHelper.getInstance()._toast(s);
    }

    private void doSearch() {
        Intent _intent = new Intent(getActivity(), SearchActivity.class);
        _intent.putExtra(AppConstants.KEY_SEARCH_TAG, AppConstants.KEY_RESTAURANT_SEARCH);
        _intent.putExtra(AppConstants.KEY_SEARCH_INDEX, 0);
        startActivity(_intent);
    }


    @Override
    public void onItemClick(View view, int position, IBaseModel item) {
        if (item == null) {
            if (view.getId() == R.id.rl_person_order)
                toPersonOrder();
            else toRecommend();
            return;
        }
        Object o = item.getEntity().get(0);
        if (o instanceof BannerTypeUrlBean.BannerEntity) {
            BannerTypeUrlBean.BannerEntity bannerEntity = (BannerTypeUrlBean.BannerEntity) item.getEntity().get(position);
            Intent intent = new Intent(getContext(), ShopDetailActivity.class);
            intent.putExtra(AppConstants.KEY_MERCHANT_ID, bannerEntity.getID());
            intent.putExtra(AppConstants.KEY_MERCHANT_LOGO, bannerEntity.getSrc());
            startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
            return;
        }
        if (o instanceof FavorableFoodUrlBean.FavorableFoodEntity) {
            FavorableFoodUrlBean.FavorableFoodEntity favorableFoodEntity = (FavorableFoodUrlBean.FavorableFoodEntity) item.getEntity().get(position);
            Intent intent = new Intent(getContext(), HotFoodActivity.class);
            intent.putExtra(AppConstants.KEY_PRODUCT_NAME, favorableFoodEntity.getTitle());
            startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
            return;
        }
        if (o instanceof MerchantUrlBean.MerchantBean) {
            Intent intent = new Intent(getContext(), ShopDetailActivity.class);
            MerchantUrlBean.MerchantBean data = (MerchantUrlBean.MerchantBean) item.getEntity().get(position);
            intent.putExtra(AppConstants.KEY_MERCHANT_ID, data.getID());
            intent.putExtra(AppConstants.KEY_MERCHANT_NAME, data.getName());
            intent.putExtra(AppConstants.KEY_MERCHANT_LOGO, data.getPic());
            startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());

        }
    }

    @Override
    public void onItemLongClick(View view, int position, IBaseModel item) {

    }

    private void toPersonOrder() {
        startActivity(new Intent(getContext(), PersonalOrderActivity.class), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    private void toRecommend() {
        startActivity(new Intent(getContext(), RecommendActivity.class), ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @OnClick(R.id.tv_home_search)
    public void onClick() {
        doSearch();
    }

    @Override
    public void showHomeData(HomeBean data) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        bannerModel.setRecommendShopBeanEntity(data.getBannerEntityList());
        favorableTypeModel.setRecommendShopBeanEntity(data.getFavorableFoodEntityList());
        commonListModel.setRecommendShopBeanEntity(data.getRecommendShopBeanEntityList());
        recycleMultiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {

    }
}
