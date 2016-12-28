package com.wlx.mobiletv.activity;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.wlx.mobiletv.adapter.HomeViewPagerAdapter;
import com.wlx.mobiletv.widget.PageIndicator;
import com.wlx.mtvlibrary.base.BaseActivity;

import butterknife.InjectView;


/**
 * 作者：LucianWang
 * 日期：2016/12/14 14:00
 * 邮箱：wlx3079@163.com
 * 描述：主界面
 */

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.vp_home)
    ViewPager vpHome;
    @InjectView(R.id.rl_container)
    RelativeLayout rlContainer;
    @InjectView(R.id.pi_home)
    PageIndicator piHome;
    /**
     * vpHome的适配器
     */
    private HomeViewPagerAdapter adapter;

    @Override
    protected void init() {
        vpHome.measure(0, 0);
        int pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 5.0f);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) vpHome.getLayoutParams();
        if (lp == null) {
            lp = new RelativeLayout.LayoutParams(pagerWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pagerWidth;
        }
        vpHome.setLayoutParams(lp);//设置页面宽度为屏幕的3/5
        vpHome.setPageMargin(getResources().getDimensionPixelSize(R.dimen._50));
        adapter = new HomeViewPagerAdapter(this);
        vpHome.setAdapter(adapter);
        //去掉滑到边缘后模糊效果
        vpHome.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        vpHome.setOffscreenPageLimit(adapter.getCount());
        piHome.setViewPager(vpHome);
        piHome.setCurrentItem(adapter.getCount() / 2);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return vpHome.dispatchTouchEvent(event);
    }
}
