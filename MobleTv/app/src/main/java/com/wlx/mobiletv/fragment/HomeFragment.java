package com.wlx.mobiletv.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wlx.mobiletv.activity.R;
import com.wlx.mobiletv.adapter.HomeFragmentRVAdapter;
import com.wlx.mobiletv.widget.MySwipeRefreshLayout;
import com.wlx.mtvlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 作者：LucianWang
 * 日期：2016/12/28 15:21
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: 2016/12/28 BaseFragment
    private String TAG = "HomeFragment";

    @InjectView(R.id.rv_home_fragment)
    RecyclerView rvHomeFragment;
    @InjectView(R.id.swipeLayout)
    MySwipeRefreshLayout swipeLayout;
    /**
     * RecycleView适配器
     */
    private HomeFragmentRVAdapter rvAdapter;
    /**
     * 数据源
     */
    private List mList;
    /**
     * RecycleView的管理器
     */
    private GridLayoutManager manager;
    /**是否正在上拉加载*/
    private boolean isLoading = false;
    /**是否下拉刷新*/
    private boolean isRefresh = false;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public void init() {
        initContainer();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    private void initContainer() {
        swipeLayout.setOnRefreshListener(this);
        manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 || position == rvAdapter.getItemCount() ? 2 : 1;
            }
        });
        rvHomeFragment.setLayoutManager(manager);
        rvHomeFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // TODO: 2017/1/4 自定义个RecycleView带下拉上拉
                visibleItemCount = manager.getChildCount();
                totalItemCount = manager.getItemCount();
                pastVisiblesItems = manager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if (!isRefresh && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        isRefresh = true;
                        // 判断点
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isLoading = false;
                                Log.i(TAG, "run: 正在加载数据");
                                initData(5);
                                rvAdapter.notifyDataSetChanged();
                            }
                        }, 2000);
                    }
                }
            }
        });
        initData(4);
    }

    private void initData(int count) {
        mList = new ArrayList();
        if (mList != null)
            mList.clear();
        for (int i = 0; i < count; i++) {
            mList.add(i);
        }
        rvAdapter = new HomeFragmentRVAdapter(getActivity(), mList);
        rvHomeFragment.setAdapter(rvAdapter);
    }

    /**
     * 下拉刷新的时候
     */
    @Override
    public void onRefresh() {
        isRefresh = true;
        Log.i(TAG, "onRefresh: "+swipeLayout.isRefreshing());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefresh = false;
                swipeLayout.setRefreshing(false);
                initData(6);
                rvAdapter.notifyDataSetChanged();
            }
        }, 2000);

    }
}
