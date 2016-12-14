package com.rhg.qf.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.View;

import com.easemob.EMCallBack;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.rhg.qf.R;
import com.rhg.qf.easeui.EaseConstant;
import com.rhg.qf.easeui.controller.EaseUI;
import com.rhg.qf.easeui.ui.EaseChatFragment;
import com.rhg.qf.runtimepermissions.PermissionsManager;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.ToastHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatActivity extends BaseAppcompactActivity {
    public static ChatActivity activityInstance;
    String toChatUsername;
    String uname;
    private EaseChatFragment chatFragment;

    @Override
    protected void initData() {
        activityInstance = this;
        uname = "QF" + dealUName(AccountUtil.getInstance().getNickName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermissionAndSetIfNecessary(new String[]{Manifest.permission_group.MICROPHONE});
        doLogin();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_chat;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_click).setIcon(R.drawable.ic_delete_white);
        menu.findItem(R.id.action_click).setTitle(R.string.delete);
        return super.onPrepareOptionsMenu(menu);
    }

    public void onGrant() {
    }

    public void onDeny(final String permission) {
        Snackbar.make(getWindow().getDecorView(), "授权失败，将影响您的体验", Snackbar.LENGTH_LONG)
                .setAction("重新授权", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(ChatActivity.this,
                                new String[]{permission}, null);
                    }
                }).show();

    }

    private void doLogin() {
        if (EMChat.getInstance().isLoggedIn()) {
            initView();
        } else
            createAccountToServer(uname, getPwd(uname), new EMCallBack() {
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginToServer(uname, getPwd(uname));
                        }
                    });
                }

                @Override
                public void onError(final int i, String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (i == EMError.NONETWORK_ERROR) {
                                ToastHelper.getInstance()._toast("网络不可用");
                            } else if (i == EMError.USER_ALREADY_EXISTS) {
                                loginToServer(uname, getPwd(uname));
                            }
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EaseUI.getInstance().getNotifier().reset();

    }


    private void initView() {
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        chatFragment = new EaseChatFragment();
        chatFragment.setHasOptionsMenu(true);
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    private void loginToServer(String nickName, String pwd) {
        EMChatManager.getInstance().login(nickName, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                //Log.i("RHG", "登录成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                    }
                });
            }

            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                //Log.i("RHG", "登录失败" + " i= " + i + " s= " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastHelper.getInstance()._toast("正在获取客服聊天...");

                    }
                });
            }
        });
    }


    private String getPwd(String nickName) {
        return "jjms" + nickName + "jjms";
    }

    private String dealUName(String uname) {
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(uname);
        String s = mat.replaceAll("");
        return s.replace(" ", "");
    }

    private void createAccountToServer(final String uname, final String pwd, final EMCallBack callBack) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMChatManager.getInstance().createAccountOnServer(uname, pwd);
                    if (callBack != null)
                        callBack.onSuccess();
                } catch (EaseMobException e) {
                    if (callBack != null)
                        callBack.onError(e.getErrorCode(), e.getMessage());
                }
            }
        });
        thread.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        if (chatFragment != null)
            chatFragment.onBackPressed();
        else finish();
    }


}
