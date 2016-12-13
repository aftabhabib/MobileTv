package com.wlx.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.wlx.mobiletv.R;

/**
 * 作者：LucianWang
 * 日期：2016/12/7 17:28
 * 邮箱：wlx3079@163.com
 * 描述：自定义倒计时
 */

public class CountDown extends View {
    private static final String TAG = "CountDown";
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 倒计时
     */
    private float mTime;
    /**
     * 倒计时字体的包裹
     */
    private Rect mRect;
    /**
     * 倒计时画弧度包裹
     */
    private RectF mRectF;
    /**
     * 倒计时描边颜色
     */
    private int colorCountDownExternal;
    /**
     * 倒计时内部颜色
     */
    private int colorCountDownCenter;
    /**
     * 剩余倒计时时间
     */
    private String currentTime;
    /**
     * 控件宽度
     */
    private int mWidth;
    /**
     * 控件宽度
     */
    private int mHeight;
    /**
     * 半径
     */
    private float mRadius;
    /**
     * 描边宽度
     */
    private float mStorkeWidth;
    /**
     * 画的角度
     */
    private float angle;
    /**
     * 每刷新一次画的角度
     */
    private float every_angle;
    /**
     * 每次刷新的时间
     */
    private final int refresh_time = 50;
    /**
     * 开启倒计时的消息
     */
    private final int START = 101, STOP = 102;
    /**
     * 当前状态
     */
    private int CURRENT_STATE = -1;
    /**
     * 状态种类
     */
    private int CURRENT_STARTING = 1, CURRENT_STOP = 2;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case START: {
                    CURRENT_STATE = CURRENT_STARTING;
                    if (mTime <= 0) {
                        mHandler.sendEmptyMessage(STOP);
                    } else {
                        mTime -= refresh_time;
                        angle += every_angle;
                        invalidate();
                        mHandler.sendEmptyMessageDelayed(START, refresh_time);
                    }
                    break;
                }
                case STOP: {
                    CURRENT_STATE = CURRENT_STOP;
                    mHandler.removeMessages(START);
                    break;
                }
            }
            return true;
        }
    });

    public CountDown(Context context) {
        this(context, null);
    }

    public CountDown(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public CountDown(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setFakeBoldText(true);
        //// TODO: 2016/12/13 自定义属性
        mPaint.setTextSize(50);
        mRadius = 300;
        mTime = 3;
        angle = 0;
        mStorkeWidth = 20;
        every_angle = 120 / ((mTime * 1000) / refresh_time);
        mRect = new Rect();
        colorCountDownCenter = getResources().getColor(R.color.colorCountDownCenter);
        colorCountDownExternal = getResources().getColor(R.color.colorCountDownExternal);
        //布局加载完成监听（可以获取到宽、高）
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = getMeasuredWidth();
                mHeight = getMeasuredHeight();
                mRectF = new RectF(mWidth / 2 - mRadius + mStorkeWidth, mHeight / 2 - mRadius + mStorkeWidth, mWidth / 2 + mRadius - mStorkeWidth, mHeight / 2 + mRadius - mStorkeWidth);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画底
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(colorCountDownExternal);
        mPaint.setStrokeWidth(mStorkeWidth);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
        //画弧度
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(colorCountDownCenter);
        Log.i(TAG, "onDraw: " + angle);
        canvas.drawArc(mRectF, 0, angle, true, mPaint);
        //画字
        mPaint.setColor(Color.BLACK);
        currentTime = String.valueOf((int) mTime / 1000);
        mPaint.getTextBounds(currentTime, 0, currentTime.length(), mRect);
        canvas.drawText(currentTime, (mWidth - mRect.width()) / 2, (mHeight + mRect.height()) / 2, mPaint);

    }

    /**
     * 设置倒计时时间
     *
     * @param time 倒计时时间（秒）
     */
    public CountDown setmTime(float time) {
        mTime = time;
        return this;
    }

    /**
     * 开启倒计时
     */
    public void start() {
        if (CURRENT_STATE != CURRENT_STARTING)
            mHandler.sendEmptyMessage(START);
    }

    /**
     * 结束倒计时
     */
    public void stop() {
        if (CURRENT_STATE != CURRENT_STOP) {
            mHandler.sendEmptyMessage(STOP);
        }
    }

}
