package com.wlx.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：wlx
 * 日期：2016/12/7 17:28
 * 邮箱：wlx3079@163.com
 */

public class CountDown extends View{
    /**画笔*/
    private Paint mPaint = new Paint();

    public CountDown(Context context) {
        this(context, null);
    }

    public CountDown(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public CountDown(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(100,100,50,mPaint);


    }
}
