package com.qianwang.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sky on 2017/4/6.
 */

public class LoadingCustomView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int defaultSize = 500;
    private int minRadiu=25;
    private int maxRadiu=100;
    private int minoffset=10;

    public LoadingCustomView(Context context) {
        this(context, null);
        initPaint();
    }

    public LoadingCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
    }

    public LoadingCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        mPaint=new Paint();
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
                mHeight = maxRadiu;
            }
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawCircle(minoffset+minRadiu,mHeight/2,minRadiu,mPaint);


    }

}
