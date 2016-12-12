package com.wlx.mtvlibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;

/**
 * 作者：LucianWang
 * 日期：2016/12/12 14:42
 * 邮箱：wlx3079@163.com
 * 说明：Activity的基类
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**设置布局*/
        if (getLayoutId() != -1)
            setContentView(getLayoutId());
        else if (getLayoutView() != null)
            setContentView(getLayoutView());
        /**初始化视图（abstract）*/
        init();
        /**初始化监听*/
        initLinstener();
        /**ButterKnife*/
        ButterKnife.inject(this);
    }


    @Override
    protected void onDestroy() {super.onDestroy();}

    protected abstract void init();

    protected void initLinstener() {}

    protected abstract int getLayoutId();

    protected View getLayoutView() {return null;}

}
