package com.qianwang.menu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;


/**
 * Created by sky on 2017/4/5.
 */

public class MenuCustom extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int defaultsize = 500;
    private int radiu = 50;
    private int rectWidthOffset = 20;  //环形宽度在整个view布局的偏移
    private int rectHeightOffset = 12;  //环形高度在整个view布局的偏移
    private int rectDegree = 20;  //环形的环度
    private Rect mCenterMenuRect;  //中间圆形所在区域矩阵
    private boolean isOpen = false;
    private ValueAnimator mAnimatorBackground;
    private float mAnimatorValue;
    private int rectWidth = 40;   //小正方形的宽度
    private int menuAnimCount = 1;  //第一个菜单
    private float menuAnimatorValue1;
    private float menuAnimatorValue2;
    private float menuAnimatorValue3;
    private float menuAnimatorValue4;
    private ValueAnimator menuValueAnimator;

    public MenuCustom(Context context) {
        this(context, null);
        initPaint();
    }

    public MenuCustom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
    }

    public MenuCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    private void initPaint() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (modeWidth != MeasureSpec.EXACTLY) {

            if (modeHeight == MeasureSpec.EXACTLY) {

                mHeight = sizeHeight;
                mWidth = defaultsize;

            } else {
                mWidth = defaultsize;
                mHeight = defaultsize;
            }
        }

        if (modeWidth == MeasureSpec.EXACTLY) {
            if (modeHeight != MeasureSpec.EXACTLY) {
                mWidth = sizeWidth;
                mHeight = defaultsize;
            } else {

                mWidth = sizeWidth;
                mHeight = sizeHeight;
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint rectRoundPaint = new Paint();
        rectRoundPaint.setAntiAlias(true);
        rectRoundPaint.setStrokeWidth(5);
        rectRoundPaint.setColor(Color.GRAY);
        rectRoundPaint.setStyle(Paint.Style.FILL);

        canvas.translate(mWidth / 2, mHeight / 2);
        float nowwidth = (mWidth / 2 - rectWidthOffset) * mAnimatorValue;
        RectF rectF = new RectF(-nowwidth, -radiu + rectHeightOffset, nowwidth, radiu - rectHeightOffset);
        canvas.drawRoundRect(rectF, rectDegree, rectDegree, rectRoundPaint);


        Paint rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStrokeWidth(5);
        rectPaint.setColor(Color.WHITE);
        rectPaint.setStyle(Paint.Style.FILL);


        canvas.drawRect((-4 * rectWidth - radiu / 2)*menuAnimatorValue1, -rectWidth / 2*menuAnimatorValue1, (-3 * rectWidth - radiu / 2)*menuAnimatorValue1, rectWidth / 2*menuAnimatorValue1, rectPaint);
        canvas.drawRect((-2 * rectWidth - radiu / 2)*menuAnimatorValue2, -rectWidth / 2*menuAnimatorValue2, (-rectWidth - radiu / 2)*menuAnimatorValue2, rectWidth / 2*menuAnimatorValue2, rectPaint);

        canvas.drawRect((rectWidth + radiu / 2) * menuAnimatorValue3, -rectWidth / 2 * menuAnimatorValue3, (2 * rectWidth + radiu / 2) * menuAnimatorValue3, rectWidth / 2 * menuAnimatorValue3, rectPaint);
        canvas.drawRect((3 * rectWidth + radiu / 2) * menuAnimatorValue4, -rectWidth / 2 * menuAnimatorValue4, (4 * rectWidth + radiu / 2) * menuAnimatorValue4, rectWidth / 2 * menuAnimatorValue4, rectPaint);



        canvas.drawCircle(0, 0, radiu, mPaint);
        canvas.rotate(360 * mAnimatorValue, 0, 0);


        Paint linePain = new Paint();
        linePain.setColor(Color.WHITE);
        linePain.setAntiAlias(true);
        linePain.setStrokeWidth(5);
        linePain.setStyle(Paint.Style.STROKE);
       /* canvas.drawLine(0,-20,-20,20,linePain);
        canvas.drawLine(0,-20,20,20,linePain);*/

        canvas.drawLine(0, 0, -20, 20, linePain);
        canvas.drawLine(0, 0, 20, 20, linePain);
        canvas.drawLine(0, 0, -20, -20, linePain);
        canvas.drawLine(0, 0, 20, -20, linePain);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        int l = w / 2 - radiu;
        int t = h / 2 - radiu;
        int r = w / 2 + radiu;
        int b = w / 2 + radiu;

        mCenterMenuRect = new Rect(l, t, r, b);

    }

    private void startAnimator(final int from, int end) {

        if (mAnimatorBackground != null && mAnimatorBackground.isRunning()) {
            mAnimatorBackground.cancel();
            mAnimatorBackground = null;
        }
        mAnimatorBackground = ValueAnimator.ofFloat(from, end).setDuration(500);
        mAnimatorBackground.setInterpolator(new OvershootInterpolator());
        mAnimatorBackground.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        menuValueAnimator = ValueAnimator.ofFloat(from, end).setDuration(200);
        menuValueAnimator.setInterpolator(new OvershootInterpolator());
        menuValueAnimator.setRepeatCount(4);
        menuValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                switch (menuAnimCount) {

                    case 1:
                        menuAnimatorValue1 = (float) valueAnimator.getAnimatedValue();

                        break;
                    case 2:
                        menuAnimatorValue2 = (float) valueAnimator.getAnimatedValue();
                        break;
                    case 3:
                        menuAnimatorValue3 = (float) valueAnimator.getAnimatedValue();
                        break;
                    case 4:
                        menuAnimatorValue4 = (float) valueAnimator.getAnimatedValue();
                        break;

                }

                postInvalidate();
            }
        });

        menuValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                if (from == 1) {
                    Log.i("520it", "menuAnimCount" + "**************************"+menuAnimCount);
                    menuAnimCount++;
                } else {
                    menuAnimCount--;
                }
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();

        if (from == 1) {
            menuAnimCount=1;
            Log.i("520it", "from" + "**************************"+from);
            animatorSet.play(mAnimatorBackground).after(menuValueAnimator);
        } else {
            menuAnimCount=4;
            Log.i("520it", "from" + "**************************"+from);
            animatorSet.play(menuValueAnimator).after(mAnimatorBackground);
        }

        animatorSet.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (mCenterMenuRect.contains(x, y)) {

                    Log.i("520it", "ACTION_DOWN" + "*************  click *************");
                    openOrClose();

                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }


        return super.onTouchEvent(event);
    }

    private void openOrClose() {


        Log.i("520it", "" + "*************  openOrClose  *************");
        if (isOpen) {

            startAnimator(1, 0);
        } else {

            startAnimator(0, 1);
        }

        isOpen = !isOpen;
    }

    /**
     * dp转px
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
