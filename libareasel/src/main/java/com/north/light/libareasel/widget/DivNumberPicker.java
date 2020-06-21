package com.north.light.libareasel.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * author:li
 * date:2020/6/21
 * desc:自定义number picker
 */
public class DivNumberPicker extends NumberPicker {

    public DivNumberPicker(Context context) {
        super(context);
    }

    public DivNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DivNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    public void updateView(View view) {
        if (view instanceof EditText) {
            //这里修改显示字体的属性，主要修改颜色
            ((EditText) view).setTextSize(12);
        }
    }
}