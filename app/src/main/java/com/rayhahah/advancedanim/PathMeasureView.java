package com.rayhahah.advancedanim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @Github https://github.com/Rayhahah
 * @time 2017/4/3
 * @fuction 原生实现路径绘制动画
 * @use
 */
public class PathMeasureView extends View {
    private Paint mPaint;
    private Path mCirclePath;
    private Path mDstPath;
    private PathMeasure mPathMeasure;
    private float mLength;
    private ValueAnimator mValueAnimator;
    private float mAnimatedValue;
    private boolean mIsWindow;
    private boolean mIsArrow;
    private float[] mPos = new float[2];
    private float[] mTan = new float[2];

    public PathMeasureView(Context context) {
        super(context);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setWindow(boolean window) {
        mIsWindow = window;
        invalidate();
    }

    public void setArrow(boolean arrow) {
        mIsArrow = arrow;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //必须要有lineTo（0,0）才可以实现路径的完整绘制
        mDstPath.reset();
        mDstPath.lineTo(0, 0);
        float stopD = mAnimatedValue * mLength;
        float startD = 0;
        if (mIsWindow) {
            //通过设置其实位置的变化来实现Window加载风格
            startD = (float) (stopD - ((0.5 - Math.abs(mAnimatedValue - 0.5)) * mLength));
        }

        //获取当前进度的路径，同时赋值给传入的mDstPath
        mPathMeasure.getSegment(startD, stopD, mDstPath, true);

        canvas.save();
        canvas.translate(300, 300);
        canvas.drawPath(mDstPath, mPaint);
        canvas.restore();


        if (mIsArrow) {
            //mPos是当前路径点的坐标
            //mTan通过下面公式可以得到当前点的切线角度
            mPathMeasure.getPosTan(mAnimatedValue * mLength, mPos, mTan);
            float degree = (float) (Math.atan2(mTan[1], mTan[0]) * 180 / Math.PI);
            Log.e("lzh", "onDraw:  degree=" + degree);
            canvas.save();
            canvas.translate(300, 300);
            canvas.drawCircle(mPos[0], mPos[1], 10, mPaint);
            canvas.rotate(degree);
            //绘制切线
            canvas.drawLine(0, -200, 200, -200, mPaint);
            canvas.restore();

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        //完整的圆的路径
        mCirclePath = new Path();
        //路径绘制每段截取出来的路径
        mDstPath = new Path();

        mCirclePath.addCircle(0, 0, 200, Path.Direction.CW);

        //路径测量类
        mPathMeasure = new PathMeasure();
        //测量路径
        mPathMeasure.setPath(mCirclePath, true);

        //获取被测量路径的总长度
        mLength = mPathMeasure.getLength();

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取从0-1的变化值
                mAnimatedValue = (float) animation.getAnimatedValue();
                //不断刷新绘图，实现路径绘制
                invalidate();
            }
        });
    }

    public void startAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.start();
        }
    }

    public void pauseAnim() {
        if (mValueAnimator != null
                && mValueAnimator.isStarted()
                && mValueAnimator.isRunning()) {
            mValueAnimator.pause();
        }
    }


}
