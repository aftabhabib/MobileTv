package com.wlx.mtvlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wlx.mtvlibrary.base.engine.BaseAF;

import butterknife.ButterKnife;

/**
 * 作者：LucianWang
 * 日期：2016/12/28 15:22
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public abstract class BaseFragment extends Fragment implements BaseAF{
    /**
     * 是否可见
     */
    private boolean isVisioble = false;
    /**
     * 父类的Activity
     */
    protected BaseActivity baseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.inject(this, view);
        /**ButterKnife*/
        ButterKnife.inject(getActivity());
        /**初始化视图（abstract）*/
        init();
        /**初始化监听*/
        initLinstener();
        return view;
    }

    protected void initLinstener() {}

    /**
     * 懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisioble = true;
            onFragmentVisible();
        } else {
            isVisioble = false;
            onFragmentInVisible();
        }
    }

    /**可见*/
    protected void onFragmentVisible() {
    }
    /**不可见*/
    protected void onFragmentInVisible() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
