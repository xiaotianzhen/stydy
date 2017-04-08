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
    private int state = -1;
    private static int SUCCESS = 1;
    private static int FAIL = 0;
    private boolean tag=true;


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

        if (Math.abs(movex) < halfWidth) {

            canvas.drawCircle(Math.abs(movex), 0, minRadiu, mPaint);
        }

        if (Math.abs(movex) > halfWidth / 4 && Math.abs(movex) < 5 * halfWidth / 4) {

            canvas.drawCircle(Math.abs(movex) - halfWidth / 4, 0, minRadiu, mPaint);
        }
        if (Math.abs(movex) > 2 * halfWidth / 4 && Math.abs(movex) < 6 * halfWidth / 4) {

            canvas.drawCircle(Math.abs(movex) - 2 * halfWidth / 4, 0, minRadiu, mPaint);

        }
        if (Math.abs(movex) > 3 * halfWidth / 4 && Math.abs(movex) < 7 * halfWidth / 4) {

            canvas.drawCircle(Math.abs(movex) - 3 * halfWidth / 4, 0, minRadiu, mPaint);
        }

        if (Math.abs(movex) > 7 * halfWidth / 4) {

            canvas.drawCircle(halfWidth + Math.abs(movex) - 7 * halfWidth / 4, 0, minRadiu, mPaint);
        }
        if (Math.abs(movex) > 8 * halfWidth / 4) {

            canvas.drawCircle(halfWidth + Math.abs(movex) - 8 * halfWidth / 4, 0, minRadiu, mPaint);
        }
        if (Math.abs(movex) > 9 * halfWidth / 4) {

            canvas.drawCircle(halfWidth + Math.abs(movex) - 9 * halfWidth / 4, 0, minRadiu, mPaint);
        }
        if (Math.abs(movex) > 10 * halfWidth / 4) {

            canvas.drawCircle(halfWidth + Math.abs(movex) - 10 * halfWidth / 4, 0, minRadiu, mPaint);
        }
        if (Math.abs(movex) > halfWidth && Math.abs(movex) < 7 * halfWidth / 4) {

            float radiu = minRadiu / 2 + Math.abs(movex) * minRadiu / (7 * halfWidth / 4);
            radiu = (radiu > minRadiu) ? radiu : minRadiu;
            canvas.drawCircle(mWidth / 2, 0, radiu, mPaint);
        }

        if (Math.abs(movex) > 7 * halfWidth / 4 && Math.abs(movex) < (10 * halfWidth / 4)) {

            float radiu = 2 * minRadiu - Math.abs(movex) * minRadiu / (10 * halfWidth / 4);
            radiu = (radiu > minRadiu) ? radiu : minRadiu;
            canvas.drawCircle(mWidth / 2, 0, radiu, mPaint);
        }

        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5);
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);

        if (state == 1) {

            if (Math.abs(movex) > halfWidth && Math.abs(movex) == 7 * halfWidth / 4) {
                canvas.translate(mWidth / 2, 0);
                canvas.drawCircle(0, 0,  3*minRadiu / 2, mPaint);
                canvas.drawLine(-maxRadiu / 2,0, 0,maxRadiu / 3,linePaint);
                canvas.drawLine( 0,maxRadiu / 3,2*maxRadiu/4,-maxRadiu/3,linePaint);
            }
        }
        if (state == 0) {

            if (Math.abs(movex) > halfWidth && Math.abs(movex) == 7 * halfWidth / 4) {
                Log.i("520it", "" + "************* fail *************");
                canvas.translate(mWidth / 2, 0);
                canvas.drawCircle(0, 0,  3*minRadiu / 2, mPaint);
                canvas.drawLine(-maxRadiu / 3, -maxRadiu / 3, maxRadiu / 3, maxRadiu / 3, linePaint);
                canvas.drawLine(-maxRadiu / 3, maxRadiu / 3, maxRadiu / 3, -maxRadiu / 3, linePaint);

            }
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

                if (state < 0) {
                    if (Math.abs(movex) < 2 * mWidth) {
                        movex += 10;
                        postInvalidate();
                    } else {
                        movex = 0;
                    }
                } else {
                    if(tag){
                        movex=0;
                    }

                    int halfWidth = mWidth / 2;
                    if (Math.abs(movex) < 7 * halfWidth / 4) {
                        movex += 10;
                        postInvalidate();
                    }

                    tag=false;

                }

            }
        });

        mMinAnimator.start();
    }

    public void success() {
        state = SUCCESS;
    }

    public void faile() {
        state = FAIL;
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
