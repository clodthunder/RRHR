package com.lskj.gx.rrhr.app.base;

import android.app.Application;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.lskj.gx.rrhr.log.LogLevel;
import com.lskj.gx.rrhr.log.Logger;
import com.lskj.gx.rrhr.log.MLogAdapter;
import com.lskj.gx.rrhr.utils.NetworkStatusManager;
import com.lskj.gx.rrhr.utils.ToastUtil;

/**
 * Created by Home on 16/7/14.
 */
public class MApplication extends Application {
    public static MApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplication = this;

        //初始化Toast
        ToastUtil.init(this);
        //初始化日志管理
        initLogg();
        //初始化网络管理
        NetworkStatusManager.init(this);
        initStetho();

    }

    public void initLogg() {
        Logger.init("RRHR")                 // default PRETTYLOGGER or use just init()
                .methodCount(0)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logAdapter(new MLogAdapter()); //default AndroidLogAdapter
    }

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this).enableDumpapp(new DumperPluginsProvider() {
            @Override
            public Iterable<DumperPlugin> get() {
                return new Stetho.DefaultDumperPluginsBuilder(mApplication)
                        .finish();
            }
        }).enableWebKitInspector(Stetho.defaultInspectorModulesProvider(mApplication)).build());
    }

    /**
     * 配制图片加载
     */
    public void initGilide() {

    }


    public static MApplication getmApplication() {
        return mApplication;
    }
}
