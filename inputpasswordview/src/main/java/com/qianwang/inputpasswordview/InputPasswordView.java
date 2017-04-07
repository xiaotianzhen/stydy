package com.qianwang.inputpasswordview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

/**
 * Created by sky on 2017/3/29.
 */

public class InputPasswordView extends View {

    private int width;
    private int height;
    private int count = 6;     //密码输入个数
    private int size = 80;  //宽度默认值
    private Paint mPaint;
    private InputMethodManager input;
    private ArrayList<Integer> result = new ArrayList<Integer>();
    public InputCallback mCallback;



    public InputPasswordView(Context context) {
        this(context, null);
        initPaint();
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    public InputPasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    public InputPasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }


    private void initPaint() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);

        input = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.setOnKeyListener(new MyKeyListener());


    }



    /**
     *
     * 完成回调
     */

    public interface InputCallback {

        void onInputFinish(String str);
    }

    public void setCallBack(InputCallback callBack) {
        this.mCallback = callBack;


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeigeht = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        if (modeWidth != MeasureSpec.EXACTLY) {

            if (modeHeigeht == MeasureSpec.EXACTLY) {
                Log.i("520it", "" + "***************   111111  ***********");
                height = sizeHeight / 2;
                width = height * count;   //确定高度
            } else {

                height = size;
                width = size * 6;    //两个都是wrap_content
            }
        }

        if (modeWidth == MeasureSpec.EXACTLY) {

            if (modeHeigeht != MeasureSpec.EXACTLY) {
                Log.i("520it", "" + "************  3333333  **************");
                width = sizeWidth / 2;
                height = width / count;    //宽度确定
            } else {
                width = sizeWidth / 2;
                height = sizeHeight / 2;     //两个都是确定值
            }
        }
        setMeasuredDimension(width, height);

        Log.i("520it", "" + "**************************" + width + "********" + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth = width - 2;
        int mHeigth = height - 2;
        RectF rect = new RectF(0, 0, mWidth, mHeigth);

        canvas.drawRoundRect(rect, 10, 10, mPaint);

        int dotRaidu = mHeigth / 6;

        for (int i = 0; i < 6; i++) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.GRAY);
            if(result.size()>i){

                int x = (int) (mHeigth * (i + 0.5));
                int y = mHeigth / 2;

                canvas.drawCircle(x, y, dotRaidu, paint);
            }


            if (i < 5) {
                canvas.drawLine(mWidth / 6 * (i + 1), 0, mWidth / 6 * (i + 1), mHeigth, paint);
            }

        }

    }

    /***
     * 设置焦点，弹出软键盘
     *
     * @param event
     * @return
     */


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (MotionEvent.ACTION_DOWN == event.getAction()) {

            requestFocus();
            input.showSoftInput(this, InputMethodManager.SHOW_FORCED);
            return true;

        }
        return super.onTouchEvent(event);
    }

    /**
     * 焦点发生改变隐藏
     *
     * @param hasWindowFocus
     */

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        if (!hasWindowFocus) {
            input.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }

    }

    /**
     * 为了告诉系统，我这个view可以接受输入
     *
     * @return
     */


    @Override
    public boolean onCheckIsTextEditor() {

        return true;
    }

    class MyKeyListener implements OnKeyListener {

        @Override
        public boolean onKey(View view, int keycode, KeyEvent keyEvent) {


            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                if (keycode >= KeyEvent.KEYCODE_0 && keycode <= KeyEvent.KEYCODE_9) {
                    if (result.size() < count) {
                        result.add(keycode - 7);
                        invalidate();
                        ensureInputFinish();
                        return true;
                    }
                }
            }

            if (keyEvent.getAction() == KeyEvent.KEYCODE_DEL) {
                Log.i("520it", "" + "**************  KEYCODE_DEL  ************");
                if (!result.isEmpty()) {
                    result.remove(result.size() - 1);
                    invalidate();
                }
                return true;
            }
            if (keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                Log.i("520it", "" + "**************  KEYCODE_DEL  ************");
                ensureInputFinish();
                return true;
            }


            return false;
        }

        /**
         *
         * 确定完成
         */

        private void ensureInputFinish() {


            if(result.size()==count&&mCallback!=null){

                StringBuilder sb=new StringBuilder();

                for(int i:result){
                    sb.append(i);
                }
                mCallback.onInputFinish(sb.toString());
            }
        }
    }





    /**
     *
     * 当系统弹出输入法时，会与目标view建立一个链接，然后传输文本等等给view
     * @param outAttrs
     * @return
     */

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {

             outAttrs.inputType= InputType.TYPE_CLASS_NUMBER;
             outAttrs.imeOptions=EditorInfo.IME_ACTION_DONE;


        return new MyInputConnection(this,false);
    }

    class MyInputConnection extends BaseInputConnection{

        public MyInputConnection(View targetView, boolean fullEditor) {
            super(targetView, fullEditor);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {

            Log.i("520it", "" + "**************  commitText  ************");
            return super.commitText(text,newCursorPosition);
        }

        /**
         * 这里，我们重写了delete。。。方法，因为按下软键盘的DEL会触发这个方法，我们手动模拟发送KeyEvent给view。
         * 为什么不在这里直接操作？因为…如果拥有’硬’键盘，就是外接物理键盘的手机，他DEL还是会触发的，我们统一处理
         * @param beforeLength
         * @param afterLength
         * @return
         */
        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            Log.i("520it", "" + "*************   deleteSurroundingText   *************");
            //软键盘的删除键 DEL 无法直接监听，自己发送del事件
            if (beforeLength==1 && afterLength==0){
                return super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }

    }
}
