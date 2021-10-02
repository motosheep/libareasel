package com.north.light.libareasel.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.north.light.libareasel.R;


public class AddressSlideBar extends View {

    private static final String[] BAR_LETTERS = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint paint = new Paint();
    private int selectIndex = -1;//选项的数组下标
    private onTouchLetterListener onTouchLetterListener;
    private boolean isShowBkg = false;//背景是否变化

    public AddressSlideBar(Context context) {
        super(context);
    }

    public AddressSlideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddressSlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public interface onTouchLetterListener {
        void onTouchLetterChange(String letter, boolean touch);
    }

    public void setOnTouchLetterListener(onTouchLetterListener onTouchLetterListener) {
        this.onTouchLetterListener = onTouchLetterListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowBkg) {
            canvas.drawColor(getResources().getColor(R.color.color_FFFFFF));//显示背景
        }
        int height = getHeight();
        int width = getWidth();
        int singleHeight = (height - 20) / BAR_LETTERS.length;//每个字母占得高度
        for (int i = 0; i < BAR_LETTERS.length; i++) {//遍历字母
            paint.setColor(getResources().getColor(R.color.color_4d000000));
            paint.setTextSize(30);
            paint.setAntiAlias(true);//设置抗锯齿样式
            if (i == selectIndex) {//如果被选中
                paint.setColor(getResources().getColor(R.color.color_000000));
                paint.setFakeBoldText(true);//加粗
            }
            //从左下角开始绘制
            float xpos = (width - paint.measureText(BAR_LETTERS[i])) / 2;//x轴
            float ypos = singleHeight * i + singleHeight;//y轴 （i从0开始算）
            canvas.drawText(BAR_LETTERS[i], xpos, ypos, paint);//开始绘制
            paint.reset();//重置画笔
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float ypos = event.getY();
        int oldSelectIndex = selectIndex;//上一次触发的坐标
        int newSelectIndex = (int) (ypos / getHeight() * BAR_LETTERS.length);//根据触摸的位置确定点的哪个字母
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isShowBkg = true;
                if (oldSelectIndex != newSelectIndex && newSelectIndex >= 0 && newSelectIndex <= BAR_LETTERS.length) {
                    if (onTouchLetterListener != null) {
                        String letter;
                        if (newSelectIndex >= BAR_LETTERS.length) {
                            letter = BAR_LETTERS[BAR_LETTERS.length - 1];
                        } else {
                            letter = BAR_LETTERS[newSelectIndex];
                        }
                        onTouchLetterListener.onTouchLetterChange(letter, true);//注册点击事件并传入字母
                    }
                    oldSelectIndex = newSelectIndex;
                    selectIndex = newSelectIndex;
                    invalidate();//重新绘制

                }
                break;
            case MotionEvent.ACTION_UP:
                isShowBkg = false;
                selectIndex = -1;
                invalidate();
                if (onTouchLetterListener != null) {
                    String letter;
                    if (newSelectIndex < 0) {
                        letter = BAR_LETTERS[0];
                    } else if (newSelectIndex >= BAR_LETTERS.length) {
                        letter = BAR_LETTERS[BAR_LETTERS.length - 1];
                    } else {
                        letter = BAR_LETTERS[newSelectIndex];
                    }
                    onTouchLetterListener.onTouchLetterChange(letter, false);//注册点击事件并传入字母
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldSelectIndex != newSelectIndex && newSelectIndex >= 0 && newSelectIndex <= BAR_LETTERS.length) {
                    if (onTouchLetterListener != null) {
                        String letter;
                        if (newSelectIndex >= BAR_LETTERS.length) {
                            letter = BAR_LETTERS[BAR_LETTERS.length - 1];
                        } else {
                            letter = BAR_LETTERS[newSelectIndex];
                        }
                        onTouchLetterListener.onTouchLetterChange(letter, true);//注册点击事件并传入字母
                    }
                    oldSelectIndex = newSelectIndex;
                    selectIndex = newSelectIndex;
                    invalidate();//重新绘制
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}