package com.rhg.qf.update.interfaceimpl;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.update.UpdaterConfiguration;
import com.rhg.qf.update.interfacedef.UpdateCheckUIHandler;
import com.rhg.qf.update.utils.UIHandleUtils;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.ToastHelper;

import butterknife.ButterKnife;


/**
 * Caiyuan Huang
 * <p>2016/10/27</p>
 * <p>默认的提示更新UI处理</p>
 */
public final class DefaultUpdateCheckUIHandler implements UpdateCheckUIHandler {
    private UpdaterConfiguration mConfig;
    private Context mContext;

    public DefaultUpdateCheckUIHandler(UpdaterConfiguration config) {
        this.mConfig = config;
    }

    @Override
    public void setContext(Context context) {
        if (!(context instanceof Activity)) {
            throw new RuntimeException("context must be instance of activity");
        }
        this.mContext = context;
    }

    @Override
    public void hasUpdate() {
        /*表明用户不更新当前版本，将跳过更新*/
        if (AccountUtil.getInstance().getIgnoreVersion() == mConfig.getUpdateInfo().getUpdateVersionCode()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(UIHandleUtils.getString(R.string.update_tips));
        View view = LayoutInflater.from(mContext).inflate(R.layout.update_layout, null);
        ((TextView) ButterKnife.findById(view, R.id.update_new_version)).append(mConfig.getUpdateInfo().getUpdateVersionName());
        ((TextView) ButterKnife.findById(view, R.id.update_size)).append(String.valueOf(mConfig.getUpdateInfo().getUpdateSize()));
        ((TextView) ButterKnife.findById(view, R.id.update_time)).append(mConfig.getUpdateInfo().getUpdateTime());
        ((TextView) ButterKnife.findById(view, R.id.update_detail)).append(mConfig.getUpdateInfo().getUpdateInfo());
        builder.setView(view);
//        builder.setMessage(mConfig.getUpdateInfo().getUpdateInfo());
        if (!mConfig.getUpdateInfo().isForceInstall()) {
            builder.setNegativeButton(UIHandleUtils.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.setPositiveButton(UIHandleUtils.getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mConfig.getDownloader().download();
            }
        });
        builder.setNeutralButton(UIHandleUtils.getString(R.string.ignore), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccountUtil.getInstance().setIgnoreVersion(mConfig.getUpdateInfo().getUpdateVersionCode());
//                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    @Override
    public void noUpdate() {
        ToastHelper.getInstance()._toast(UIHandleUtils.getString(R.string.no_update));
    }

    @Override
    public void checkError(String error) {
        ToastHelper.getInstance()._toast(UIHandleUtils.getString(R.string.check_error));
    }


}
