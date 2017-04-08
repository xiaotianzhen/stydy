/*
package com.qianwang.loading;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;


public class LoadingCustomView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int defaultSize = 700;
    private int minRadiu = 25;
    private int maxRadiu = 50;
    private ValueAnimator mMinAnimator1;
    private int count = 0;
    private float animatorValue1;
    private float animatorValue2;
    private float animatorValue3;
    private float animatorValue4;

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
        mPaint.setColor(Color.RED);
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
                mWidth = defaultSize;
                mHeight = 2 * maxRadiu;
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (animatorValue1 != 0) {

            canvas.drawCircle((mWidth / 2 - 11 * minRadiu - maxRadiu) * animatorValue1, mHeight / 2, minRadiu, mPaint);
        }

        if (animatorValue2 != 0) {

            canvas.drawCircle((mWidth / 2 - 8 * minRadiu - maxRadiu) * animatorValue2, mHeight / 2, minRadiu, mPaint);
        }

        if (animatorValue3 != 0) {

            canvas.drawCircle((mWidth / 2 - 5 * minRadiu - maxRadiu) * animatorValue3, mHeight / 2, minRadiu, mPaint);
        }


        if (animatorValue4 != 0) {

            canvas.drawCircle((mWidth / 2 - 2 * minRadiu - maxRadiu) * animatorValue4, mHeight / 2, minRadiu, mPaint);
        }


        canvas.drawCircle(mWidth / 2, mHeight / 2, maxRadiu, mPaint);


       */
/* canvas.drawCircle(mWidth/2+2*minRadiu+maxRadiu,mHeight/2,minRadiu,mPaint);
        canvas.drawCircle(mWidth/2+5*minRadiu+maxRadiu,mHeight/2,minRadiu,mPaint);
        canvas.drawCircle(mWidth/2+8*minRadiu+maxRadiu,mHeight/2,minRadiu,mPaint);
        canvas.drawCircle(mWidth/2+11*minRadiu+maxRadiu,mHeight/2,minRadiu,mPaint);*//*




    }


    private void startAnimator(final int from, int end) {


        mMinAnimator1 = ValueAnimator.ofFloat(from, end).setDuration(500);
        mMinAnimator1.setInterpolator(new OvershootInterpolator());
        mMinAnimator1.setRepeatCount(4);
        mMinAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {


                Log.i("520it", "count" + "**************************" + count);
                switch (count) {
                    case 1:
                        animatorValue1 = (float) valueAnimator.getAnimatedValue();
                        break;
                    case 2:
                        animatorValue2 = (float) valueAnimator.getAnimatedValue();
                        break;

                    case 3:
                        animatorValue3 = (float) valueAnimator.getAnimatedValue();
                        break;

                    case 4:
                        animatorValue4 = (float) valueAnimator.getAnimatedValue();
                        break;
                }

                postInvalidate();
            }
        });

        mMinAnimator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.i("520it", "" + "***********  onAnimationStart ***************");
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

                Log.i("520it", "" + "***********  onAnimationRepeat***************");
                if (from == 0) {
                    count++;
                } else {
                    Log.i("520it", "count22222" + "**************************" + count);
                    count++;
                }
            }
        });

        mMinAnimator1.start();
    }

}
*/
