package com.wlx.mobiletv.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wlx.mobiletv.activity.R;

/**
 * 作者：LucianWang
 * 日期：2016/12/26 16:50
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public class PageIndicator extends View {

    private static final String TAG = "PageIndicator";
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 传过来的ViewPager
     */
    private ViewPager mViewPager;
    /**
     * 当前状态
     */
    private int CURRENT_STATE = 0;
    /**
     * 当前选中条目
     */
    private int CURRENT_ITEM = 0;
    /**
     * 内部的半径
     */
    private float mCenterRadius = getResources().getDimension(R.dimen._7);
    /**
     * 描边的半径
     */
    private float mStorkeRadius = getResources().getDimension(R.dimen._11);
    /**描边的宽度*/
    private float mStorkeWidth = getResources().getDimension(R.dimen._2);
    /**
     * ViewPager的监听
     */
    private ViewPager.OnPageChangeListener mOnpageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            CURRENT_ITEM = position;
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            CURRENT_STATE = state;
        }
    };
    /**
     * ViewPager的item数量
     */
    private int totleCount;
    /**
     * 两点间距
     */
    private int STEP = 36;
    /**
     * ViewPager的适配器
     */
    private PagerAdapter mAdapter;

    public PageIndicator(Context context) {
        this(context, null);
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mAdapter == null)
            return;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(mStorkeRadius+CURRENT_ITEM * STEP, mStorkeRadius, mCenterRadius, mPaint);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(mStorkeWidth);
        for (int i = 0; i < mAdapter.getCount(); i++) {
            canvas.drawCircle(mStorkeRadius+i * STEP,mStorkeRadius, mStorkeRadius, mPaint);
        }
    }

    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
        mAdapter = mViewPager.getAdapter();
        totleCount = mViewPager.getAdapter().getCount();
        mViewPager.setOnPageChangeListener(mOnpageChangeListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        Log.i(TAG, "onMeasure: width="+width+"...height="+height);
        setMeasuredDimension(width, height);
    }

    /**
     * 测量宽度
     */
    private int measureWidth(int measureSpec) {
        int widthMode = MeasureSpec.getMode(measureSpec), widthSize = MeasureSpec.getSize(measureSpec);
        return widthMode == MeasureSpec.EXACTLY ? widthSize : (int) ((mStorkeRadius * 2) + (totleCount - 1) * STEP);
    }

    /**
     * 测量高度
     */
    private int measureHeight(int measureSpec) {
        int heightMode = MeasureSpec.getMode(measureSpec), heightSize = MeasureSpec.getSize(measureSpec);
        return heightMode == MeasureSpec.EXACTLY ? heightSize : (int) mStorkeRadius*2;
    }

    /**
     * 设置当前选中item
     */
    public void setCurrentItem(int currentItem) {
        if (currentItem > 0 && currentItem < mAdapter.getCount()) {
            mViewPager.setCurrentItem(currentItem);
            invalidate();
        }
    }
}
