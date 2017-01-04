package com.wlx.mobiletv.activity;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.wlx.mobiletv.adapter.HomeFragmentAdapter;
import com.wlx.mobiletv.fragment.FragmentFactory;
import com.wlx.mtvlibrary.base.BaseActivity;
import com.wlx.mtvlibrary.base.BaseFragment;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 作者：LucianWang
 * 日期：2016/12/14 14:00
 * 邮箱：wlx3079@163.com
 * 描述：主界面
 */

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.btn_home)
    Button btnHome;
    @InjectView(R.id.btn_myself)
    Button btnMyself;
    @InjectView(R.id.vp_home_container)
    ViewPager vpHomeContainer;
    /**主页Fragment*/
    private BaseFragment homeFragment;
    /**我的InfoFragment*/
    private BaseFragment myinfoFragment;
    /***/
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    @Override
    public void init() {
        /**初始化首页布局*/
        initHomeFragment();
    }

    private void initHomeFragment() {
        homeFragment =  FragmentFactory.createFragment(FragmentFactory.HOME_FRAGMENT);
        myinfoFragment = FragmentFactory.createFragment(FragmentFactory.MYINFO_FRAGMENT);
        mFragments.add(homeFragment);
        mFragments.add(myinfoFragment);
        vpHomeContainer.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager(),mFragments));
    }

    @OnClick({R.id.btn_home, R.id.btn_myself})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                vpHomeContainer.setCurrentItem(0);
                break;
            case R.id.btn_myself:
                vpHomeContainer.setCurrentItem(1);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }
}
