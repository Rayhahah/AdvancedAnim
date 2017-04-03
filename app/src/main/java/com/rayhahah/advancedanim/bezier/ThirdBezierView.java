package com.rayhahah.advancedanim.bezier;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by a on 2017/4/1.
 */

public class ThirdBezierView extends View {
    private int mStartX;
    private int mStartY;
    private int mFlagOneX;
    private int mFlagOneY;
    private int mFlagTwoX;
    private int mFlagTwoY;
    private int mEndX;
    private int mEndY;
    private Path mPath;
    private Paint pathPaint;
    private Paint pointPaint;
    private Paint linePaint;
    private boolean mIsMoreTouch;
    private ValueAnimator mValueAnimator;
    private float mMoveX;
    private float mMoveY;
    private Paint mMovePaint;

    public ThirdBezierView(Context context) {
        super(context);
    }

    public ThirdBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThirdBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartX = w / 8;
        mStartY = h / 2;

        mFlagOneX = w / 8 * 3;
        mFlagOneY = h / 2;

        mFlagTwoX = w / 8 * 5;
        mFlagTwoY = h / 2;

        mEndX = w / 8 * 7;
        mEndY = h / 2;

        mPath = new Path();

        pathPaint = new Paint();
        setPaint(pathPaint, Color.BLACK, 5);
        pointPaint = new Paint();
        setPaint(pointPaint, Color.BLUE, 10);
        linePaint = new Paint();
        setPaint(linePaint, Color.GRAY, 3);

        mMovePaint = new Paint();
        mMovePaint.setAntiAlias(true);
        mMovePaint.setStyle(Paint.Style.FILL);
        mMovePaint.setColor(Color.parseColor("#ff5831"));
    }

    private void setPaint(Paint paint, int color, int strokeWidth) {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(mStartX, mStartY);
        //画出三阶贝塞尔曲线！
        mPath.cubicTo(mFlagOneX, mFlagOneY, mFlagTwoX, mFlagTwoY, mEndX, mEndY);

        canvas.drawPoint(mStartX, mStartY, pointPaint);
        canvas.drawPoint(mFlagOneX, mFlagOneY, pointPaint);
        canvas.drawPoint(mFlagTwoX, mFlagTwoY, pointPaint);
        canvas.drawPoint(mEndX, mEndY, pointPaint);

        canvas.drawLine(mStartX, mStartY, mFlagOneX, mFlagOneY, linePaint);
        canvas.drawLine(mFlagOneX, mFlagOneY, mFlagTwoX, mFlagTwoY, linePaint);
        canvas.drawLine(mFlagTwoX, mFlagTwoY, mEndX, mEndY, linePaint);

        canvas.drawCircle(mMoveX, mMoveY, 10, mMovePaint);

        canvas.drawPath(mPath, pathPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() &
                //多指解码，只有添加这个条件才可以识别多指触控
                MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                mIsMoreTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mFlagOneX = (int) event.getX(0);
                mFlagOneY = (int) event.getY(0);
                if (mIsMoreTouch) {
                    mFlagTwoX = (int) event.getX(1);
                    mFlagTwoY = (int) event.getY(1);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mIsMoreTouch = false;
//                startAnim();
                break;
            case MotionEvent.ACTION_UP:
                startAnim();
                break;

        }
        return true;
    }

    private void startAnim() {

        mValueAnimator = ValueAnimator.ofObject(
                new ThirdBezierEvalutor(new PointF(mFlagOneX, mFlagOneY), new PointF(mFlagTwoX, mFlagTwoY)),
                new PointF(mStartX, mStartY),
                new PointF(mEndX, mEndY)
        );
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mMoveX = pointF.x;
                mMoveY = pointF.y;
                invalidate();
            }
        });
        mValueAnimator.start();


    }

    class ThirdBezierEvalutor implements TypeEvaluator<PointF> {

        private PointF mFlagOnePoint;
        private PointF mFlagTwoPoint;

        public ThirdBezierEvalutor(PointF flagOnePoint, PointF flagTwoPoint) {
            mFlagOnePoint = flagOnePoint;
            mFlagTwoPoint = flagTwoPoint;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return BezierUtil.CalculateBezierPointForCubic(fraction, startValue, mFlagOnePoint, mFlagTwoPoint, endValue);
        }
    }

}
