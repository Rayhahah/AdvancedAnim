package com.rayhahah.advancedanim.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @Github https://github.com/Rayhahah
 * @time 2017/4/3
 * @fuction 触摸画笔绘制，使用贝塞尔曲线优化线条
 * @use
 */
public class DrawPathView extends View {
    private Path mPath;
    private Paint mPathPaint;
    private Paint mPointPaint;
    private float mX;
    private float mY;
    private float rX;
    private float rY;

    public DrawPathView(Context context) {
        super(context);

    }

    public DrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        mPathPaint = new Paint();
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setColor(Color.BLACK);
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStrokeWidth(5);
        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointPaint.setColor(Color.BLUE);
        mPointPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPathPaint);
        canvas.drawPoint(rX, rY, mPointPaint);

    }


    /**
     * 触摸绘制
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mX = event.getX();
                mY = event.getY();
                mPath.moveTo(mX, mY);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();


                rX = (x + mX) / 2;
                rY = (y + mY) / 2;

                mPath.quadTo(mX, mY, rX, rY);
                mX = x;
                mY = y;

                break;
        }
        invalidate();
        return true;
    }
}
