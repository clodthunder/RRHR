package com.lskj.gx.rrhr.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lskj.gx.rrhr.R;

/**
 * Created by Home on 16/7/15.
 */
public class MAlterDialog extends AlertDialog implements View.OnClickListener {

    private MDialogClickCallBack mDialogClickCallBack;
    private static MAlterDialog mAlertDialog;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static MAlterDialog getMAlertDialog() {
        if (mAlertDialog == null) {
            synchronized (MAlterDialog.class) {
                if (mAlertDialog == null) {
                    mAlertDialog = new MAlterDialog(mContext);
                }
            }
        }
        return mAlertDialog;
    }

    public void setmDialogClickCallBack(MDialogClickCallBack mDialogClickCallBack) {
        this.mDialogClickCallBack = mDialogClickCallBack;
    }

    public MAlterDialog(Context context) {
        super(context);
        mContext = context;
    }

    protected MAlterDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    protected MAlterDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custmer);
        Button left = (Button) findViewById(R.id.btn_alert_left);
        Button right = (Button) findViewById(R.id.btn_alert_right);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mDialogClickCallBack.onCusDialogClick(view);
    }

    public interface MDialogClickCallBack {
        void onCusDialogClick(View view);
    }
}
