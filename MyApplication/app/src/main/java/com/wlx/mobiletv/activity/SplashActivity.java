package com.wlx.mobiletv.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.wlx.mobiletv.impl.CountDownListener;
import com.wlx.mobiletv.widget.CountDown;
import com.wlx.mtvlibrary.base.BaseActivity;

import butterknife.InjectView;

/**
 * 作者：LucianWang
 * 日期：2016/12/6 17:23
 * 邮箱：wlx3079@163.com
 * 描述：自定义倒计时
 */

public class SplashActivity extends BaseActivity{

    /**自定义倒计时控件*/
    @InjectView(R.id.cd)
    CountDown cd;

    /**延时进入主界面*/
    private int DELAYED_TIME = 3000;
    /**倒计时监听*/
    private CountDownListener mCountDownListener = new CountDownListener() {
        @Override
        public void onFinish() {
            startActivity(new Intent(SplashActivity.this,HomeActivity.class));
            finish();
        }
        @Override
        public void onRun() {}
        @Override
        public void onRunning() {}
    };

    @Override
    protected void init() {
        cd.setTime(DELAYED_TIME).setCountDownListener(mCountDownListener).start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
