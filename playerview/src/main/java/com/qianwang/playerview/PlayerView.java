package com.qianwang.playerview;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by sky on 2017/3/31.
 */

public class PlayerView extends View {

    private static final int DRAW_CIRCLE = 10001;
    private static final int ROTATE_TRANSLATE = 10002;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Paint mCirclePaint;
    private int radius = 50;
    private int size = 200; //默认宽高
    private ValueAnimator mValueAnimator;
    private float mAnimatorValue;
    private float startSegment; //圆开始画的长度
    private int mCurrentState = DRAW_CIRCLE;  //
    private Path dst;
    private PathMeasure mMeasure;  //测量path
    private Path path;  //三角形的path
    private Path circlePath;//圆形的path
    private long duration = 800;    //执行时间


    public PlayerView(Context context) {
        super(context);
        initPaint();
        initAnimation();
        mValueAnimator.start();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initAnimation();
        mValueAnimator.start();

    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAnimation();
        mValueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeigth = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (modeWidth == MeasureSpec.EXACTLY) {

            if (modeHeigth != MeasureSpec.EXACTLY) {

                mWidth = sizeWidth;
                mHeight = mWidth;

            } else {
                mWidth = sizeWidth;
                mHeight = sizeHeight;
            }

        }

        if (modeWidth != MeasureSpec.EXACTLY) {

            if (modeHeigth == MeasureSpec.EXACTLY) {
                mHeight = sizeHeight;
                mHeight = mWidth;
            } else {
                mHeight = size;
                mWidth = size;
            }
        }

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w - getPaddingLeft() - getPaddingRight();
        mHeight = h - getPaddingTop() - getPaddingBottom();
        initPath();
    }

    private void initPath() {

        dst = new Path();

        Point p1 = new Point();
        p1.x = -(int) ((radius / 2 * Math.tan(30 * Math.PI / 180)));
        p1.y = -radius / 2;
        Point p2 = new Point();
        p2.x = p1.x;
        p2.y = radius / 2;
        Point p3 = new Point();
        p3.x = (int) (radius / 2 / Math.sin(60 * Math.PI / 180));
        p3.y = 0;
        path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.close();


        circlePath = new Path();
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        circlePath.addArc(rectF, 268, 358);

        mMeasure = new PathMeasure(circlePath, false);
        circlePath.close();

    }

    private void initAnimation() {

        TimeInterpolator interpolator = new AccelerateDecelerateInterpolator();
        mValueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);
        mValueAnimator.setInterpolator(interpolator);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);   //重复
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE); //无限数量
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mAnimatorValue = (Float) mValueAnimator.getAnimatedValue();//这里我们将会拿到一个0-1的值

                invalidate(); // 这里进行重绘

            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
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
                switch (mCurrentState) {
                    case DRAW_CIRCLE:
                        mCurrentState = ROTATE_TRANSLATE;
                        break;
                    case ROTATE_TRANSLATE:
                        mCurrentState = DRAW_CIRCLE;
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);  //将坐标原点移到布局中点位置
        dst.reset();


        switch (mCurrentState) {

            //画圆
            case DRAW_CIRCLE:

                //画三角形
               canvas.drawPath(path,mPaint);

                startSegment = (float) (mMeasure.getLength() /5 * ((0.3 - mAnimatorValue) > 0 ? (0.3 - mAnimatorValue) : 0));
                mMeasure.getSegment(startSegment,mMeasure.getLength()*mAnimatorValue,dst,true);  //截取路径片段
                canvas.drawPath(dst,mCirclePaint);

                break;

            //三角形旋转和圆的缩回
            case ROTATE_TRANSLATE:

                //旋转三角形  画布旋转
                canvas.rotate(360*mAnimatorValue);
                canvas.drawPath(path,mPaint);

                //画圆
                mMeasure.getSegment(mMeasure.getLength()*mAnimatorValue,mMeasure.getLength(),dst,true);
                canvas.drawPath(dst,mCirclePaint);
                break;
        }

        super.onDraw(canvas);
    }

    private void initPaint() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(6);

        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.RED);


    }
}
