package com.wlx.mobiletv;


import com.wlx.mtvlibrary.base.BaseActivity;
import com.wlx.widget.CountDown;

import butterknife.InjectView;

/**
 * 作者：LucianWang
 * 日期：2016/12/6 17:23
 * 邮箱：wlx3079@163.com
 * 描述：自定义倒计时
 */

public class SplashActivity extends BaseActivity {


    @InjectView(R.id.cd)
    CountDown cd;

    @Override
    protected void init() {
        CountDown cd = (CountDown) findViewById(R.id.cd);
        cd.setmTime(9000).start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }


}
