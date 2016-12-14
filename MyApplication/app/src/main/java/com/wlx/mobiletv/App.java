package com.wlx.mobiletv;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 作者：LucianWang
 * 日期：2016/12/14 14:06
 * 邮箱：wlx3079@163.com
 * 描述：APP入口
 */

public class App extends Application {

    private static App mApp;
    /**主线程ID*/
    private static int mMainThreadId;
    /**主线程Handler*/
    private static Handler mMainHandler;

    @Override
    public void onCreate() {
        mApp = this;
        mMainThreadId = android.os.Process.myTid();
        mMainHandler = new Handler();
        /**加载视频流地址*/
        loadVideoUrl();
        super.onCreate();
    }
    private void loadVideoUrl() {

    }
    /**获取全局Context*/
    public static Context getApplicationConext(){
        return mApp;
    }
    /**获取主线程ID*/
    public static int getMainThreadId() {
        return mMainThreadId;
    }
    /**获取主线程Handler*/
    public static Handler getmMainHandler() {
        return mMainHandler;
    }
}
