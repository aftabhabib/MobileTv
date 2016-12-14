package com.wlx.mobiletv.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wlx.mtvlibrary.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：LucianWang
 * 日期：2016/12/14 14:32
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public class VideoActivity extends BaseActivity {


    @Override
    protected void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }
}
