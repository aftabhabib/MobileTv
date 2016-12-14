package com.wlx.mobiletv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.wlx.mtvlibrary.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：LucianWang
 * 日期：2016/12/14 14:00
 * 邮箱：wlx3079@163.com
 * 描述：主界面
 */

public class HomeActivity extends BaseActivity {
    @InjectView(R.id.button)
    Button button;

    @Override
    protected void init() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @OnClick(R.id.button)
    public void onClick() {
        startActivity(new Intent(this, VideoActivity.class));
    }
}
