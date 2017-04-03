package com.rayhahah.advancedanim.bezier;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @Github https://github.com/Rayhahah
 * @time 2017/4/2
 * @fuction 波浪动画
 * @use
 */
public class WaveView extends View implements View.OnClickListener {
    private int mViewWidth;
    private int mViewHeight;
    private int mCenterHeight;
    private int mWaveLength;
    private int mWaveCount;
    //第一层波浪
    private Path mPathOne;
    private Path mPathTwo;
    private Paint mPaintOne;
    private Paint mPaintTwo;
    private Path mPathThree;
    private Paint mPaintThree;
    private int mColorOne = Color.parseColor("#72c8ff");
    private int mColorTwo = Color.parseColor("#b9e8ff");
    private int mColorThree = Color.parseColor("#997ce8ef");
    private int mGradient;
    private ValueAnimator mValueAnimator;
    private boolean mIsPlaying;
    private float mOffset;
    private boolean mIsTouchable = true;
    private boolean mIsCircle = true;
    private Path mPathCircle;
    private int mRadius;


    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mGradient = 60;

        mWaveLength = 700;

        mViewWidth = w;
        mViewHeight = h;

        mCenterHeight = h / 2;

        mRadius = mWaveLength / 2;


        initPathAndPaint();

        setOnClickListener(this);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPathCircle.reset();
        mPathCircle.addCircle(mViewWidth / 2, mViewHeight / 2, mRadius, Path.Direction.CCW);

        drawWave(canvas, mPathTwo, mPaintTwo, (int) (mOffset + 120), -20,
                mWaveLength, mCenterHeight, mIsCircle);
        drawWave(canvas, mPathOne, mPaintOne, (int) (mOffset + 0), 0,
                mWaveLength, mCenterHeight, mIsCircle);
        drawWave(canvas, mPathThree, mPaintThree, (int) (mOffset - 80), -10,
                mWaveLength, mCenterHeight + 30, mIsCircle);


    }

    /**
     * 绘制波浪
     *
     * @param canvas
     * @param path
     * @param paint
     * @param offsetX
     */
    private void drawWave(Canvas canvas, Path path, Paint paint,
                          int offsetX, int offsetY,
                          int waveLength, int centerHeight,
                          boolean isCircle) {
        path.reset();
        mWaveCount = (int) Math.round(mViewWidth / waveLength + 1.5);
        path.moveTo(-waveLength + offsetX, centerHeight);

        int i = 0;

        for (i = 0; i < mWaveCount; i++) {
            //绘制全部n型的贝塞尔曲线
            path.quadTo(-waveLength * 3 / 4 + i * waveLength + offsetX,
                    centerHeight - mGradient - offsetY,
                    -waveLength / 2 + i * waveLength + offsetX,
                    centerHeight);
            //绘制全部U型的贝塞尔曲线
            path.quadTo(-waveLength * 1 / 4 + i * waveLength + offsetX,
                    centerHeight + mGradient + offsetY,
                    i * waveLength + offsetX,
                    centerHeight);
        }

        /**
         * 将绘制的路径闭合
         */

        path.lineTo(mViewWidth, mViewHeight);
        path.lineTo(0, mViewHeight);
        path.lineTo(-mWaveLength, centerHeight);

        path.close();

        if (isCircle) {
            mPathCircle.op(path, Path.Op.INTERSECT);
            canvas.drawPath(mPathCircle, paint);
        } else {
            canvas.drawPath(path, paint);

        }
    }

    /**
     * 点击播放波浪动画
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    public void playAnim() {
        if (!mIsPlaying) {
            mValueAnimator.start();
            mIsPlaying = true;
        }
    }

    public void pauseAnim() {
        if (mIsPlaying) {
            if (mValueAnimator.isRunning()) {
                mValueAnimator.pause();
                mIsPlaying = false;
            }
        }
    }

    private void initValueAnimator() {
        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofFloat(0, mWaveLength);
            mValueAnimator.setDuration(750);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mOffset = ((float) animation.getAnimatedValue());
                    postInvalidate();
                }
            });
            mValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mOffset = 0;
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsTouchable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initValueAnimator();
                    if (mIsPlaying) {
                        pauseAnim();
                    } else {
                        playAnim();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCenterHeight = (int) event.getY();
                    invalidate();
                    break;
            }
        }
        return true;
    }

    private void initPathAndPaint() {

        mPathOne = new Path();
        mPathCircle = new Path();
        mPaintOne = new Paint();
        mPaintOne.setColor(mColorOne);
        mPaintOne.setAntiAlias(true);
        mPaintOne.setStrokeWidth(2);
        mPaintOne.setStyle(Paint.Style.FILL);

        mPathTwo = new Path();
        mPaintTwo = new Paint();
        mPaintTwo.setColor(mColorTwo);
        mPaintTwo.setAntiAlias(true);
        mPaintTwo.setStrokeWidth(2);
        mPaintTwo.setStyle(Paint.Style.FILL);


        mPathThree = new Path();
        mPaintThree = new Paint();
        mPaintThree.setColor(mColorThree);
        mPaintThree.setAntiAlias(true);
        mPaintThree.setStrokeWidth(2);
        mPaintThree.setStyle(Paint.Style.FILL);

    }


}
