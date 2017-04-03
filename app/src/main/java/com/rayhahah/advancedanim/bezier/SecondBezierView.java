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

public class SecondBezierView extends View {
    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;
    private int mFlagX;
    private int mFlagY;
    private Path mPath;
    private Paint pointPaint;
    private Paint linePaint;
    private Paint pathPaint;
    private ValueAnimator mValueAnimator;
    private Paint mMovePaint;
    private PointF mMovePoint;


    public SecondBezierView(Context context) {
        super(context, null);
    }

    public SecondBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public SecondBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    private void setPaint(Paint paint, int color, int strokeWidth) {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartX = w / 8;
        mStartY = h / 2;

        mFlagX = w / 2;
        mFlagY = h / 2;

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

        startAnim();
    }

    private void startAnim() {
        mValueAnimator = ValueAnimator.ofObject(
                new SecondBezierEvaluator(new PointF(mFlagX, mFlagY)),
                new PointF(mStartX, mStartY),
                new PointF(mEndX, mEndY));
        mValueAnimator.setDuration(2000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMovePoint = ((PointF) animation.getAnimatedValue());
                invalidate();
            }
        });
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.start();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartX, mStartY);
        mPath.quadTo(mFlagX, mFlagY, mEndX, mEndY);

        canvas.drawPoint(mStartX, mStartY, pointPaint);
        canvas.drawPoint(mFlagX, mFlagY, pointPaint);
        canvas.drawPoint(mEndX, mEndY, pointPaint);

        canvas.drawLine(mStartX, mStartY, mFlagX, mFlagY, linePaint);
        canvas.drawLine(mFlagX, mFlagY, mEndX, mEndY, linePaint);

        canvas.drawCircle(mMovePoint.x, mMovePoint.y, 10, mMovePaint);
        canvas.drawPath(mPath, pathPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mFlagX = (int) event.getX();
                mFlagY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                startAnim();
                break;
        }

        return true;
    }

    /**
     * 二阶贝塞尔曲线转换
     */
    class SecondBezierEvaluator implements TypeEvaluator<PointF> {

        private PointF mFlagPoint;

        public SecondBezierEvaluator(PointF flagPoint) {
            mFlagPoint = flagPoint;
        }

        /**
         * 根据当前控制点，起始点，终止点，和当前长度比例，计算出贝塞尔曲线上点的坐标
         *
         * @param fraction
         * @param startValue
         * @param endValue
         * @return
         */
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF pointF = BezierUtil.CalculateBezierPointForQuadratic(fraction, startValue, mFlagPoint, endValue);
            return pointF;
        }
    }
}
