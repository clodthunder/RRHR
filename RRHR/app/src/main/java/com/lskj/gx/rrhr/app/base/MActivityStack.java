package com.lskj.gx.rrhr.app.base;

import android.app.Activity;

import com.lskj.gx.rrhr.utils.NetworkStatusManager;

import java.util.Stack;

/**
 * Created by Home on 16/7/15.
 * Activity 栈
 */
public class MActivityStack {

    private static Stack<Activity> mActivityStack = null;

    public static Stack<Activity> getmActivityStack() {
        if (mActivityStack == null) {
            synchronized (MActivityStack.class) {
                mActivityStack = new Stack<>();
            }
        }
        return mActivityStack;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void pushActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        //取消网络监听
        NetworkStatusManager.getInstance().stopListening();
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }
}
