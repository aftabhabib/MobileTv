package com.wlx.mobiletv.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.wlx.mobiletv.activity.R;


/**
 * 作者：LucianWang
 * 日期：2016/12/22 10:00
 * 描述：圆角布局
 */
public class RoundRectLayout extends View {

    /**默认圆角*/
    private static final int NORMAL_RADIUS = 20;
    /**半径*/
    private float mRadius;
    /**画笔*/
    private Paint mShapePaint;

    public RoundRectLayout(Context context) {
        this(context, null, 0);
    }

    public RoundRectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundRectLayout);
            this.mRadius = a.getDimension(R.styleable.RoundRectLayout_radius, NORMAL_RADIUS);
            a.recycle();
        }
        setWillNotDraw(false);

        mShapePaint = new Paint();
        mShapePaint.setAntiAlias(true);
        //设置混合模式(取两层绘制交集。显示下层。)
        mShapePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas != null && mRadius > 0)
            drawPath(canvas);
        else
            super.draw(canvas);
    }
    /**
     * 绘制圆角控件
     */
    private void drawPath(Canvas shapeCanvas) {
        int width = getWidth();
        int height = getHeight();
        shapeCanvas.save();
        shapeCanvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, width, height), mRadius, mRadius, Path.Direction.CW);
        super.draw(shapeCanvas);
        shapeCanvas.drawPath(path, mShapePaint);
    }
}
