package com.wlx.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：wlx
 * 日期：2016/12/7 17:28
 * 邮箱：wlx3079@163.com
 */

public class CountDown extends View {
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 倒计时
     */
    private String mNum = "9";
    /**倒计时字体的包裹*/
    private Rect mRect;

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
        mPaint.setTextSize(50);
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画底
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        canvas.drawCircle(100, 100, 50, mPaint);
        //画弧度
        mPaint.setColor(Color.RED);
        canvas.drawArc(new RectF(0, 0, 200, 200),-90,90,true,mPaint);
        //画字
        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.BLACK);
        mPaint.getTextBounds(mNum, 0, mNum.length(), mRect);
        canvas.drawText(mNum, 100 - mRect.width() / 2, 100 + mRect.height() / 2, mPaint);
//        canvas.drawArc();
    }
}
