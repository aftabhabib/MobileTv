package com.wlx.mobiletv.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlx.mobiletv.activity.R;
import com.wlx.mobiletv.widget.PageIndicator;
import com.wlx.mobiletv.widget.RoundRectLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：LucianWang
 * 日期：2016/12/26 14:02
 * 邮箱：wlx3079@163.com
 * 描述：HomeFragment的ViewPager的Adapter
 */

public class HomeFragmentRVAdapter extends RecyclerView.Adapter {

    private static final String TAG = "HomeFragmentRVAdapter";

    /**
     * Head类型
     */
    private int TYPE_HEAD = 101;
    /**
     * 内容类型
     */
    private int TYPE_CONTEXT = 102;
    /**
     * Foot类型
     */
    private int TYPE_FOOT = 103;


    private Context context;
    /**
     * HeaderView数量
     */
    private int HEAD_VIEW_COUNT = 1;
    /**
     * FootView数量
     */
    private int FOOT_VIEW_COUNT = 1;
    /**
     * 数据源
     */
    List mList = new ArrayList();
    /**
     * 资源
     */
    private final Resources mResources;
    /**
     * 屏幕宽度像素
     */
    private final int widthPixels;

    public HomeFragmentRVAdapter(Context context, List mList) {
        this.context = context;
        this.mList = mList;
        mResources = context.getResources();
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder mHolder = null;
        if (viewType == TYPE_HEAD) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_home_headview, null);
            mHolder = new HeadHolder(view);
        } else if (viewType == TYPE_CONTEXT) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_home_center_item, null);
            mHolder = new ContentHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_home_footview, null);
            mHolder = new FootHolder(view);
        }
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadHolder) {
            initHeadView(holder);
        } else if (holder instanceof ContentHolder) {
            initContent(holder);
        } else {
            initFootView(holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position >= mList.size() + HEAD_VIEW_COUNT) {
            return TYPE_FOOT;
        } else {
            return TYPE_CONTEXT;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + HEAD_VIEW_COUNT + FOOT_VIEW_COUNT;
    }

    /**
     * 内容Holder
     */
    private class ContentHolder extends RecyclerView.ViewHolder {

        private RoundRectLayout rrlItem;

        public ContentHolder(View itemView) {
            super(itemView);
            rrlItem = (RoundRectLayout) itemView.findViewById(R.id.rrl_item);
        }
    }

    /**
     * 头Holder
     */
    private class HeadHolder extends RecyclerView.ViewHolder {

        private ViewPager vpHome;
        private PageIndicator piHome;

        public HeadHolder(View itemView) {
            super(itemView);
            vpHome = (ViewPager) itemView.findViewById(R.id.vp_home_fragment);
            piHome = (PageIndicator) itemView.findViewById(R.id.pi_home);
        }
    }

    /**
     * 脚Holder
     */
    private class FootHolder extends RecyclerView.ViewHolder {

        private TextView tvFootView;

        public FootHolder(View itemView) {
            super(itemView);
            tvFootView = (TextView) itemView.findViewById(R.id.tv_footview);
        }
    }

    /**
     * 初始化内容布局item
     */
    private void initContent(RecyclerView.ViewHolder holder) {
        ContentHolder mContentHolder = (ContentHolder) holder;
        mContentHolder.rrlItem.setBackgroundResource(R.drawable.text);
    }

    /**
     * 初始化HeadView
     */
    private void initHeadView(RecyclerView.ViewHolder holder) {
        HeadHolder mHeadHolder = (HeadHolder) holder;
        ViewGroup.LayoutParams lp = mHeadHolder.vpHome.getLayoutParams();
        lp.width = widthPixels;
        lp.height = (int) mResources.getDimension(R.dimen._400);
        mHeadHolder.vpHome.setLayoutParams(lp);
        HomeFragmentViewPagerAdapter ViewPagerAdapter = new HomeFragmentViewPagerAdapter(context);
        mHeadHolder.vpHome.setAdapter(ViewPagerAdapter);

        mHeadHolder.vpHome.setOffscreenPageLimit(ViewPagerAdapter.getCount());
        mHeadHolder.piHome.setViewPager(mHeadHolder.vpHome);
        mHeadHolder.piHome.setCurrentItem(ViewPagerAdapter.getCount() / 2);
    }

    /**
     * 初始化脚布局
     */
    private void initFootView(RecyclerView.ViewHolder holder) {
        FootHolder mFootHolder = (FootHolder) holder;
        ViewGroup.LayoutParams lp = mFootHolder.tvFootView.getLayoutParams();
        lp.width = widthPixels;
        lp.height = (int) mResources.getDimension(R.dimen._40);
        mFootHolder.tvFootView.setLayoutParams(lp);
    }
}