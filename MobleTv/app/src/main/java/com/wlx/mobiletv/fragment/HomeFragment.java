package com.wlx.mobiletv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wlx.mobiletv.activity.R;
import com.wlx.mobiletv.activity.VideoActivity;
import com.wlx.mobiletv.adapter.HomeFragmentViewPagerAdapter;
import com.wlx.mobiletv.widget.PageIndicator;
import com.wlx.mtvlibrary.base.BaseFragment;

import butterknife.InjectView;

/**
 * 作者：LucianWang
 * 日期：2016/12/28 15:21
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public class HomeFragment extends BaseFragment implements View.OnTouchListener {
    // TODO: 2016/12/28 BaseFragment

    @InjectView(R.id.rl_fragment_container)
    RelativeLayout rlFragmentContainer;
    @InjectView(R.id.vp_home_fragment)
    ViewPager vpHome;
    @InjectView(R.id.pi_home)
    PageIndicator piHome;

    /**
     * vpHome的适配器
     */
    private HomeFragmentViewPagerAdapter adapter;

    @Override
    public void init() {
        initHeadViewPager();
        rlFragmentContainer.setOnTouchListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    protected void initHeadViewPager() {
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
        adapter = new HomeFragmentViewPagerAdapter(getActivity());
        vpHome.setAdapter(adapter);
        //去掉滑到边缘后模糊效果
        vpHome.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        vpHome.setOffscreenPageLimit(adapter.getCount());
        piHome.setViewPager(vpHome);
        piHome.setCurrentItem(adapter.getCount() / 2);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getY() < vpHome.getHeight()) {
            return vpHome.dispatchTouchEvent(event);
        }
        return false;
    }
}
