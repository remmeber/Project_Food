package com.rhg.qf.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.MainAdapter;
import com.rhg.qf.adapter.viewHolder.AddressManageViewHolder;
import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.BaseAddress;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.OnItemClickListener;
import com.rhg.qf.mvp.presenter.AddOrUpdateAddressPresenter;
import com.rhg.qf.mvp.presenter.GetAddressPresenter;
import com.rhg.qf.utils.AddressUtil;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.RecycleViewDivider;
import com.rhg.qf.widget.SwipeDeleteRecycleView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/*
 *desc 地址页面
 *author rhg
 *time 2016/7/3 22:11
 *email 1013773046@qq.com
 */
public class AddressActivity extends BaseAppcompactActivity implements OnItemClickListener<CommonListModel<AddressUrlBean.AddressBean>> {

    private static final int DELETE = 0;
    private static final int MODIFY = 1;
    private static final String CHOOSE = "1";
    private static final String UNCHOOSE = "0";
    private int dialogTag = -1;
    private int pos = -1;
    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;
    @Bind(R.id.rcy_address)
    SwipeDeleteRecycleView rcyAddress;
    @Bind(R.id.srl_address)
    SwipeRefreshLayout srlAddress;
    MainAdapter<CommonListModel<AddressUrlBean.AddressBean>> addressAdapter;
    int lastPosition = -1;
    int longClickPosition = -1;
    int resultCode;
    CommonListModel<AddressUrlBean.AddressBean> addressBeanList;
    GetAddressPresenter getAddressPresenter = new GetAddressPresenter(this);
    AddOrUpdateAddressPresenter addOrUpdateAddressPresenter = new AddOrUpdateAddressPresenter(this);


    public AddressActivity() {
        addressBeanList = new CommonListModel<>();
    }

    @Override
    public void dataReceive(Intent intent) {
        if (AppConstants.ADDRESS_DEFAULT.equals(intent.getAction())) {
            resultCode = 100;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.address_layout;
    }


    @Override
    public void loadingData() {
        addressBeanList.setRecommendShopBeanEntity(AddressUtil.getAddressList());
        if (addressBeanList.getEntity().size() == 0) {
            getAddressPresenter.getAddress(AppConstants.ADDRESS_TABLE);
        }
    }

    @Override
    protected void initData() {
        flTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        tbCenterTv.setText(getResources().getString(R.string.address));
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcyAddress.setLayoutManager(linearLayoutManager);
        rcyAddress.setHasFixedSize(true);
        RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,
                SizeUtil.dip2px(8), ContextCompat.getColor(this, R.color.colorBackground));
        rcyAddress.addItemDecoration(divider);
        addressAdapter = new MainAdapter<>(
                new InflateModel(new Class[]{AddressManageViewHolder.class, View.class}, new Object[]{R.layout.item_address_content}),
                addressBeanList,
                this
        );
        rcyAddress.setAdapter(addressAdapter);
        srlAddress.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        srlAddress.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAddressPresenter.getAddress(AppConstants.ADDRESS_TABLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getAddressPresenter.getAddress(AppConstants.ADDRESS_TABLE);
    }

    @Override
    public void onBackPressed() {
        BaseAddress _addressBean = getDefaultAddress(addressBeanList.getEntity());
        if (addressBeanList.getEntity().size() > 0 && _addressBean == null) {
            ToastHelper.getInstance().displayToastWithQuickClose("请选择收货地址");
            return;
        }
        setResult(resultCode, new Intent().putExtra(AppConstants.ADDRESS_DEFAULT, _addressBean));
        super.onBackPressed();
    }

    @Override
    public void showSuccess(Object s) {
        if (s instanceof String) {
            getAddressPresenter.getAddress(AppConstants.ADDRESS_TABLE);
            return;
        }
        if (s instanceof IBaseModel) {
            addressBeanList.setRecommendShopBeanEntity(((IBaseModel) s).getEntity());
            addressAdapter.notifyDataSetChanged();
        }

        if (srlAddress.isRefreshing())
            srlAddress.setRefreshing(false);
    }

    @Override
    public void showError(Object s) {

    }

    private void selectOne(List<AddressUrlBean.AddressBean> addressBeanList, int position) {
        for (int i = 0; i < addressBeanList.size(); i++) {
            if (position == i)
                addressBeanList.get(i).setDefault(CHOOSE);
            else addressBeanList.get(i).setDefault(UNCHOOSE);
        }
    }


    @OnClick({R.id.tb_left_iv, R.id.bt_add_new_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_new_address:
                Intent _intent = new Intent(this, AddOrNewAddressActivity.class);
                startActivityForResult(_intent, AppConstants.BACK_WITH_ADD);
                break;
            case R.id.tb_left_iv:
                onBackPressed();
                /*AddressUrlBean.AddressBean _addressBean = getDefaultAddress(addressBeanList);
                setResult(resultCode, new Intent().putExtra(AppConstants.ADDRESS_DEFAULT, _addressBean));
                finish();*/
                break;
        }
    }

    private BaseAddress getDefaultAddress(List<AddressUrlBean.AddressBean> addressBeanList) {
        BaseAddress _addressBean = null;
        for (AddressUrlBean.AddressBean _address : addressBeanList) {
            if (CHOOSE.equals(_address.getDefault())) {
                _addressBean = new BaseAddress();
                _addressBean.setName(_address.getName());
                _addressBean.setPhone(_address.getPhone());
                _addressBean.setAddress(_address.getAddress());
                _addressBean.setDetail(_address.getDetail());
                break;
            }
        }
        return _addressBean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onItemClick(View view, int position, CommonListModel<AddressUrlBean.AddressBean> item) {
        switch (view.getId()) {
            case R.id.holder:
                dialogTag = DELETE;
                pos = position;
                DialogShow("确定要删除选中的地址?");
                break;
            case R.id.rl_address:
                if (position != lastPosition) {
                    selectOne(addressBeanList.getEntity(), position);
                    addressAdapter.notifyDataSetChanged();
                    lastPosition = position;
                    addOrUpdateAddressPresenter.addOrUpdateAddress(addressBeanList.getEntity().get(position).getID(),
                            null, null, null, null, AppConstants.CHOOSE_DEFAULT);
                }
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position, CommonListModel<AddressUrlBean.AddressBean> item) {
        longClickPosition = position;
        dialogTag = MODIFY;
        pos = position;
        DialogShow(getString(R.string.sure2Modify));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if (which == DialogInterface.BUTTON_NEGATIVE) {
            return;
        }
        switch (dialogTag) {
            case DELETE:
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    addOrUpdateAddressPresenter
                            .addOrUpdateAddress(addressBeanList.getEntity().get(pos).getID(),
                                    null, null, null, null, AppConstants.DELETE_ADDRESS);
                }
                break;
            case MODIFY:
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    AddressUrlBean.AddressBean _addressBean = addressBeanList.getEntity().get(pos);
                    AddressUrlBean.AddressBean addressBean = new AddressUrlBean.AddressBean();
                    Intent _intent = new Intent(AddressActivity.this, AddOrNewAddressActivity.class);
                    _intent.putExtra(AppConstants.KEY_ADDRESS, _addressBean);
                    startActivityForResult(_intent, AppConstants.BACK_WITH_UPDATE);
                }
                break;
        }
    }
}
