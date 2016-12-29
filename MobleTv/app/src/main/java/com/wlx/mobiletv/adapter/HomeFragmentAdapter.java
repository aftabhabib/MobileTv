package com.wlx.mobiletv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wlx.mtvlibrary.base.BaseFragment;

import java.util.ArrayList;

/**
 * 作者：LucianWang
 * 日期：2016/12/29 14:41
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    /**
     * Fragments所有Fragment的集合
     */
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomeFragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
