package com.rhg.qf.update.interfaceimpl;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.rhg.qf.R;
import com.rhg.qf.update.UpdaterConfiguration;
import com.rhg.qf.update.interfacedef.UpdateCheckUIHandler;
import com.rhg.qf.update.utils.UIHandleUtils;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.ToastHelper;


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
        if (AccountUtil.getInstance().getIgnoreVersion() == mConfig.getUpdateInfo().getVersionCode()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(UIHandleUtils.getString(R.string.update_tips));
        builder.setMessage(mConfig.getUpdateInfo().getUpdateInfo());
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
                AccountUtil.getInstance().setIgnoreVersion(mConfig.getUpdateInfo().getVersionCode());
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
