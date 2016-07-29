package com.lskj.gx.rrhr.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lskj.gx.rrhr.R;

/**
 * Created by Home on 16/7/15.
 * 封装Toast 可以自定义布局文件 防止多次出现
 */
public class ToastUtil {
    //控制显示
    private static Toast mToast = null;
    private static Context mContext = null;

    public static void init(Context context) {
        mContext = context;
    }


    public static Toast getToast() {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        return mToast;
    }


    /**
     * @param msg message
     */
    public static void showShort(final String msg) {
        mToast = getToast();
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * @param msg
     */
    public static void showShortWithView(final String msg) {
        View layout = View.inflate(mContext, R.layout.toast_cus_type_one, null);
        TextView mTextView = (TextView) layout.findViewById(R.id.tv_toast_type_one);
        mTextView.setText(msg);
        mToast = getToast();
        mToast.setView(layout);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * @param msg
     */
    public static void showLongWithView(final String msg) {
        View layout = View.inflate(mContext, R.layout.toast_cus_type_one, null);
        TextView mTextView = (TextView) layout.findViewById(R.id.tv_toast_type_one);
        mTextView.setText(msg);
        mToast = getToast();
        mToast.setView(layout);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }


    /**
     * @param act context
     * @param msg massage
     */
    public static void showLong(final Context act, final String msg) {
        mToast = getToast();
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

}
