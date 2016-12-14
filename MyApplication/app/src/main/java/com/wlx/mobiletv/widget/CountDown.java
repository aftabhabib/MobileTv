package com.wlx.mobiletv.widget;

import android.content.Context;
import android.content.res.TypedArray;
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

import com.wlx.mobiletv.activity.R;
import com.wlx.mobiletv.impl.CountDownListener;

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
     * 倒计时，默认3秒
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
     * 倒计时字体大小
     */
    private float mCenterTextSize;
    /**
     * 画的角度
     */
    private float angle = 0;
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
    /**
     * 倒计时监听
     */
    private CountDownListener l = null;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case START: {
                    CURRENT_STATE = CURRENT_STARTING;
                    if (mTime <= 0) {
                        if (l != null)
                            l.onFinish();
                        mHandler.sendEmptyMessage(STOP);
                    } else {
                        if (l != null)
                            l.onRunning();
                        mTime -= refresh_time;
                        angle += every_angle;
                        invalidate();
                        mHandler.sendEmptyMessageDelayed(START, refresh_time);
                    }
                    break;
                }
                case STOP: {
                    CURRENT_STATE = CURRENT_STOP;
                    if (l != null)
                        l.onFinish();
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
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        //// TODO: 2016/12/13 自定义属性
        TypedArray mArray = context.obtainStyledAttributes(attrs, R.styleable.CountDown);
        mRadius = mArray.getFloat(R.styleable.CountDown_mRadius, 50);
        mStorkeWidth = mArray.getFloat(R.styleable.CountDown_mStorkeWidth, 6);
        mCenterTextSize = mArray.getFloat(R.styleable.CountDown_mCenterTextSize, 40);
        //中心颜色
        colorCountDownCenter = mArray.getInt(R.styleable.CountDown_colorCountDownCenter, getResources().getColor(R.color.colorCountDownCenter));
        //外描边颜色
        colorCountDownExternal = mArray.getInt(R.styleable.CountDown_colorCountDownExternal, getResources().getColor(R.color.colorCountDownExternal));

        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setFakeBoldText(true);
        mPaint.setTextSize(mCenterTextSize);

        //测量字体宽高的Rect
        mRect = new Rect();

        //布局加载完成监听（可以获取到宽、高）
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = CountDown.this.getMeasuredWidth();
                Log.i(TAG, "onGlobalLayout: w" + mWidth);
                mHeight = CountDown.this.getMeasuredHeight();
                Log.i(TAG, "onGlobalLayout: h" + mHeight);
                mRectF = new RectF((mWidth + mStorkeWidth) / 2 - mRadius, (mHeight + mStorkeWidth) / 2 - mRadius, (mWidth - mStorkeWidth) / 2 + mRadius, (mHeight - mStorkeWidth) / 2 + mRadius);
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
        canvas.drawArc(mRectF, -90, angle, true, mPaint);
        //画字
        mPaint.setColor(Color.BLACK);
        currentTime = String.valueOf((int) Math.ceil(mTime / 1000));
        mPaint.getTextBounds(currentTime, 0, currentTime.length(), mRect);
        canvas.drawText(currentTime, (mWidth - mRect.width()) / 2, (mHeight + mRect.height()) / 2, mPaint);

    }

    /**
     * 设置倒计时时间
     *
     * @param time 倒计时时间（毫秒）
     */
    public CountDown setTime(float time) {
        this.mTime = time;
        every_angle = 360/ (mTime / refresh_time);
        return this;
    }

    /**
     * 设置描边颜色
     *
     * @param colorCountDownExternal 描边颜色
     */
    public CountDown setColorCountDownExternal(int colorCountDownExternal) {
        this.colorCountDownExternal = colorCountDownExternal;
        return this;
    }

    /**
     * 设置中间颜色
     *
     * @param colorCountDownCenter 中间颜色
     */
    public CountDown setColorCountDownCenter(int colorCountDownCenter) {
        this.colorCountDownCenter = colorCountDownCenter;
        return this;
    }

    /**
     * 设置半径
     *
     * @param mRadius 半径
     */
    public CountDown setRadius(float mRadius) {
        this.mRadius = mRadius;
        return this;
    }

    /**
     * 设置描边宽度
     *
     * @param mStorkeWidth 描边宽度
     */
    public CountDown setStorkeWidth(float mStorkeWidth) {
        this.mStorkeWidth = mStorkeWidth;
        return this;
    }

    /**
     * 设置中心字体大小
     *
     * @param mCenterTextSize 中心字体大小
     */
    public CountDown setCenterTextSize(float mCenterTextSize) {
        this.mCenterTextSize = mCenterTextSize;
        return this;
    }

    /**
     * 设置监听
     */
    public CountDown setCountDownListener(CountDownListener l) {
        this.l = l;
        return this;
    }

    /**
     * 开启倒计时
     */
    public void start() {
        if (CURRENT_STATE != CURRENT_STARTING) {
            if (l != null)
                l.onRun();
            mHandler.sendEmptyMessage(START);
        }
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
