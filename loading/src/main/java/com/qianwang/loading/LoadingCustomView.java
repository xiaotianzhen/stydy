package com.qianwang.loading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class LoadingCustomView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int defaultSize = 400;
    private int minRadiu = 25;
    private int maxRadiu = 50;
    private float movex;
    private ValueAnimator mMinAnimator;


    public LoadingCustomView(Context context) {
        this(context, null);
        initPaint();

    }

    public LoadingCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
        startAnimator(0, 1);

    }

    public LoadingCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (modeWidth == MeasureSpec.EXACTLY) {

            if (modeHeight != MeasureSpec.EXACTLY) {
                mWidth = sizeWidth;
                modeHeight = maxRadiu;
            } else {
                mWidth = sizeWidth;
                mHeight = sizeHeight;
            }
        }

        if (modeWidth != MeasureSpec.EXACTLY) {

            if (modeHeight == MeasureSpec.EXACTLY) {
                mHeight = sizeHeight;
                mWidth = defaultSize;

            } else {
                mWidth = dp2px(getContext(), defaultSize);
                mHeight = dp2px(getContext(), 2 * maxRadiu);
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, mHeight / 2);
        int halfWidth = mWidth / 2;
        Log.i("520it", "movex" + "**************************" + movex);
        if (Math.abs(movex) < mWidth) {

            canvas.drawCircle(Math.abs(movex), 0, minRadiu, mPaint);
        }

        if (Math.abs(movex) > halfWidth / 4) {

            canvas.drawCircle(Math.abs(movex) - halfWidth / 4, 0, minRadiu, mPaint);
        }
        if (Math.abs(movex) > 2 * halfWidth / 4) {

            canvas.drawCircle(Math.abs(movex) - 2 * halfWidth / 4, 0, minRadiu, mPaint);

        }
        if (Math.abs(movex) > 3 * halfWidth / 4) {

            canvas.drawCircle(Math.abs(movex) - 3 * halfWidth / 4, 0, minRadiu, mPaint);
        }
        if (Math.abs(movex)>halfWidth&&Math.abs(movex)<mWidth) {
            float radiu=maxRadiu-Math.abs(movex)/mWidth*minRadiu;
            radiu=(radiu>minRadiu)?radiu:minRadiu;
            canvas.drawCircle(mWidth / 2, 0, radiu, mPaint);
        }


    }

    private void startAnimator(final int from, final int end) {


        mMinAnimator = ValueAnimator.ofFloat(from, end).setDuration(1000);
        mMinAnimator.setRepeatMode(ValueAnimator.RESTART);
        mMinAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mMinAnimator.setInterpolator(new LinearInterpolator());
        mMinAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                if (Math.abs(movex) < 2 * mWidth) {
                    movex += 5;
                    postInvalidate();
                }
            }
        });

        mMinAnimator.start();
    }


    /**
     * px转dp
     *
     * @param context
     * @param dpVal
     * @return
     */
    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

}
