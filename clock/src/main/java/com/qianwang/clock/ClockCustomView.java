package com.qianwang.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sky on 2017/4/5.
 */

public class ClockCustomView extends View {

    private int mWidth;
    private int mHeight;
    private int defaultSize = 600;
    private Paint mPaint;

    public ClockCustomView(Context context) {

        this(context, null);
        initPaint();
    }

    public ClockCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
    }

    public ClockCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    private void initPaint() {

        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);


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
                mWidth = Math.min(mWidth, mHeight);
            } else {

                mWidth = defaultSize;
                mHeight = defaultSize;
            }
        }

        if (modeWidth == MeasureSpec.EXACTLY) {

            if (modeHeight != MeasureSpec.EXACTLY) {
                mWidth = sizeWidth;
                mHeight = Math.min(mWidth, defaultSize);
            } else {
                mWidth = Math.min(sizeWidth, sizeHeight);
                mHeight = Math.min(sizeWidth, sizeHeight);
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = mWidth;
        int radiu = width / 2-1;
        canvas.translate(radiu, radiu);
        canvas.drawCircle(0, 0, radiu, mPaint);

        Paint paintDegree = new Paint();
        paintDegree.setStrokeWidth(3);
        for (int i = 0; i < 60; i++) {

            if (i % 5 == 0) {

                paintDegree.setStrokeWidth(5);// 小时
                paintDegree.setTextSize(30);
                canvas.drawLine(0, radiu, 0, radiu - 60, paintDegree);
                String Degree = "12";
                if (i != 0) {
                    Degree = String.valueOf(i / 5);
                }
                canvas.drawText(Degree, 0 - paintDegree.measureText(Degree) / 2, -radiu + 90, paintDegree);

            } else {

                paintDegree.setStrokeWidth(5);// 秒
                paintDegree.setTextSize(20);
                canvas.drawLine(0, radiu, 0, radiu - 30, paintDegree);
                String Degree = String.valueOf(i);
                canvas.drawText(Degree, 0 - paintDegree.measureText(Degree) / 2, -radiu + 60, paintDegree);
            }

            canvas.rotate(6, 0, 0);
        }

        //获取当前时间
        int hour = Integer.parseInt(new SimpleDateFormat("HH").format(System.currentTimeMillis()));
        int min = Integer.parseInt(new SimpleDateFormat("mm").format(new Date()));
        int second = Integer.parseInt(new SimpleDateFormat("ss").format(new Date()));
        Log.i("520it", "hour" + "**************************"+hour);
        //画指针

        Paint hourPaint = new Paint();
        hourPaint.setStrokeWidth(20);
        hourPaint.setColor(Color.RED);
        hourPaint.setAntiAlias(true);


        canvas.save(); //保存之前的画布
        float hourdegree= (hour / 12f * 360) + (min / 60f * 15/*一个小时是15度*/);
        Log.i("520it", "hourdegree" + "**************************"+hourdegree);
        canvas.rotate(hourdegree,0,0);
        canvas.drawLine(0,0,0,-radiu/2,hourPaint);
        canvas.restore(); //将之前的画布和现在的融合

        Paint minPaint = new Paint();
        minPaint.setStrokeWidth(10);
        minPaint.setColor(Color.GREEN);
        minPaint.setAntiAlias(true);

        canvas.save();
        float mindegree= min / 60f * 360;
        canvas.rotate(mindegree,0,0);
        canvas.drawLine(0,0,0,-3*radiu/4,minPaint);
        canvas.restore();


        Paint secondPaint = new Paint();
        secondPaint.setStrokeWidth(7);
        secondPaint.setColor(Color.BLUE);
        secondPaint.setAntiAlias(true);

        canvas.save();
        float seconddegree=second*6;
        canvas.rotate(seconddegree,0,0);
        canvas.drawLine(0,0,0,-7*radiu/8,secondPaint);
        canvas.restore();


        postInvalidateDelayed(1000);

    }
}
