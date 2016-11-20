package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.AddressAdapter;
import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.DeleteItemListener;
import com.rhg.qf.mvp.presenter.AddOrUpdateAddressPresenter;
import com.rhg.qf.mvp.presenter.GetAddressPresenter;
import com.rhg.qf.ui.UIAlertView;
import com.rhg.qf.utils.AddressUtil;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.widget.RecycleViewDivider;
import com.rhg.qf.widget.SwipeDeleteRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
/*
 *desc 地址页面
 *author rhg
 *time 2016/7/3 22:11
 *email 1013773046@qq.com
 */
public class AddressActivity extends BaseAppcompactActivity {

    private static final int DELETE = 0;
    private static final int MODIFY = 1;
    private static final String CHOOSE = "1";
    private static final String UNCHOOSE = "0";
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
    AddressAdapter addressAdapter;
    int lastPosition = -1;
    int longClickPosition = -1;
    int resultCode;
    List<AddressUrlBean.AddressBean> addressBeanList;
    GetAddressPresenter getAddressPresenter = new GetAddressPresenter(this);
    AddOrUpdateAddressPresenter addOrUpdateAddressPresenter = new AddOrUpdateAddressPresenter(this);
    private DeleteItemListener deleteListener = new DeleteItemListener() {
        @Override
        public void onDelete(int position) {
            showDelDialog(position, "确定要删除选中的地址?", DELETE);
        }

        @Override
        public void onItemClick(int position) {
            if (position != lastPosition) {
                selectOne(position);
                addressAdapter.notifyDataSetChanged();
                lastPosition = position;
                addOrUpdateAddressPresenter.addOrUpdateAddress(addressBeanList.get(position).getID(),
                        null, null, null, null, AppConstants.CHOOSE_DEFAULT);
            }

        }

        @Override
        public void onLongClick(int position) {
            longClickPosition = position;
            showDelDialog(position, getResources().getString(R.string.sure2Modify), MODIFY);
        }
    };

    public AddressActivity() {
        addressBeanList = new ArrayList<>();
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
        addressBeanList = AddressUtil.getAddressList();
        if (addressBeanList.size() == 0) {
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
        addressAdapter = new AddressAdapter(this, addressBeanList);
        addressAdapter.setItemListener(deleteListener);
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
        AddressUrlBean.AddressBean _addressBean = getDefaultAddress(addressBeanList);
        setResult(resultCode, new Intent().putExtra(AppConstants.ADDRESS_DEFAULT, _addressBean));
        super.onBackPressed();
    }

    @Override
    protected void showSuccess(Object s) {
        if (s instanceof String) {
            getAddressPresenter.getAddress(AppConstants.ADDRESS_TABLE);
            return;
        }
        addressBeanList = (List<AddressUrlBean.AddressBean>) s;
        addressAdapter.setAddressBeanList(addressBeanList);
        if (srlAddress.isRefreshing())
            srlAddress.setRefreshing(false);
    }

    @Override
    protected void showError(Object s) {

    }

    private void selectOne(int position) {
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
                AddressUrlBean.AddressBean _addressBean = getDefaultAddress(addressBeanList);
                setResult(resultCode, new Intent().putExtra(AppConstants.ADDRESS_DEFAULT, _addressBean));
                finish();
                break;
        }
    }

    private AddressUrlBean.AddressBean getDefaultAddress(List<AddressUrlBean.AddressBean> addressBeanList) {
        AddressUrlBean.AddressBean _addressBean = new AddressUrlBean.AddressBean();
        for (AddressUrlBean.AddressBean _address : addressBeanList) {
            if (CHOOSE.equals(_address.getDefault())) {
                _addressBean = _address;
                break;
            }
        }
        return _addressBean;
    }

    private void showDelDialog(final int position, final String content, final int tag) {
        final UIAlertView delDialog = new UIAlertView(this, "温馨提示", content,
                "取消", "确定");
        delDialog.show();
        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                                       @Override
                                       public void doLeft() {
                                           delDialog.dismiss();
                                       }

                                       @Override
                                       public void doRight() {
                                           delDialog.dismiss();
                                           if (tag == DELETE) {
                                               addOrUpdateAddressPresenter
                                                       .addOrUpdateAddress(addressBeanList.get(position).getID(),
                                                               null, null, null, null, AppConstants.DELETE_ADDRESS);
                                           } else {
                                               AddressUrlBean.AddressBean _addressBean = addressBeanList.get(position);
                                               Intent _intent = new Intent(AddressActivity.this, AddOrNewAddressActivity.class);
                                               _intent.putExtra(AppConstants.KEY_ADDRESS, _addressBean);
                                               startActivityForResult(_intent, AppConstants.BACK_WITH_UPDATE);
                                           }
                                          /* new Handler().postDelayed(new Runnable() {
                                               @Override
                                               public void run() {
                                                   delDialog.dismiss();
                                               }
                                           }, 500);*/
                                       }
                                   }
        );
    }

    @Override
    protected void onDestroy() {
        deleteListener = null;
        super.onDestroy();
    }


}
