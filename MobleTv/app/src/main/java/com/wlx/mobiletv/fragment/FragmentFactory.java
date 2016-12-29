package com.wlx.mobiletv.fragment;


import android.util.SparseArray;

import com.wlx.mtvlibrary.base.BaseFragment;

import java.util.HashMap;

/**
 * 作者：LucianWang
 * 日期：2016/12/29 13:44
 * 邮箱：wlx3079@163.com
 * 描述：Fragment的构建工厂
 */

public class FragmentFactory {

    /**
     * 存放已经创建的Fragment
     */
    private static SparseArray<BaseFragment> mFragments = new SparseArray<>();
    /**
     * 首页Fragment
     */
    public static final int HOME_FRAGMENT = 1;
    /**
     * 首页Fragment
     */
    public static final int MYINFO_FRAGMENT = 2;

    /**
     * 创建Fragment
     *
     * @param category 需要创建的Fragment的类型，在本类里面
     * @return
     */
    public static BaseFragment createFragment(int category) {
        BaseFragment mFragment = mFragments.get(category);
        if (mFragment == null) {
            switch (category) {
                case HOME_FRAGMENT:
                    mFragment = new HomeFragment();
                    break;
                case MYINFO_FRAGMENT:
                    mFragment = new MyInfoFragment();
                    break;
            }
            mFragments.append(category, mFragment);
        }
        return mFragment;
    }

    /**
     * 移除Fragment
     *
     * @param category 需要移除的Fragment的类型
     */
    public static void removeFragment(int category) {
        if (mFragments != null && mFragments.get(category) != null)
            mFragments.remove(category);
    }

    /**
     * 清空Fragments
     */
    public static void clearFragments() {
        if (mFragments != null && mFragments.size() > 0)
            mFragments.clear();
    }
}
