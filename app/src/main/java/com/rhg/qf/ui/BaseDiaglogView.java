package com.rhg.qf.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rhg.qf.R;

/*
 *desc
 *author rhg
 *time 2016/10/19 13:45
 *email 1013773046@qq.com
 */

public abstract class BaseDiaglogView extends Dialog {

    private Context context;
    private boolean cancelable;
    private int resId;
    private UIAlertView.ClickListenerInterface clickListenerInterface;

    public BaseDiaglogView(Context context, int resId) {
        super(context, R.style.MyDialogStyle);
        this.cancelable = false;
        this.context = context;
        this.resId = resId;
    }
    public BaseDiaglogView(Context context,int resId,boolean cancelable){
        super(context,R.style.MyDialogStyle);
        this.context = context;
        this.cancelable = cancelable;
        this.resId = resId;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(cancelable);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resId, null);
        initView(view);
    }

    protected void initView(View view) {
        setContentView(view);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();

        lp.width = (int) (d.widthPixels * 0.8);
        lp.gravity = Gravity.CENTER_VERTICAL;
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(UIAlertView.ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public interface ClickListenerInterface {
        void doLeft();

        void doRight();
    }

    public class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int id = v.getId();
            switch (id) {
                case R.id.tvBtnLeft:
                    clickListenerInterface.doLeft();
                    break;
                case R.id.tvBtnRight:
                    clickListenerInterface.doRight();
                    break;

                default:
                    break;
            }
        }
    }

}
