package com.wlx.mobiletv.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wlx.mobiletv.activity.VideoActivity;


/**
 * 作者：LucianWang
 * 日期：2016/12/26 14:02
 * 邮箱：wlx3079@163.com
 * 描述：HomeFragment的ViewPager的Adapter
 */

public class HomeFragmentViewPagerAdapter extends PagerAdapter {
    private Context context;

    public HomeFragmentViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        iv.setBackgroundColor(position%2 == 0?Color.GREEN:Color.RED);
        addListener(iv);
        container.addView(iv, 0);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void addListener(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, VideoActivity.class));
            }
        });
    }

}
