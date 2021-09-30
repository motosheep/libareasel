package com.north.light.libareasel.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.north.light.libareasel.R;


/**
 * author:li
 * date:2020/6/21
 * desc:自定义number picker
 */
public class DivNumberPicker extends AreaNumberPickerView {

    public DivNumberPicker(Context context) {
        super(context);
        initData();
    }

    public DivNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public DivNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        this.setDividerColor(getContext().getResources().getColor(R.color.color_FFFFFF));
        this.setNormalTextColor(getContext().getResources().getColor(R.color.color_000000));
        this.setSelectedTextColor(getContext().getResources().getColor(R.color.color_3385FF));
    }

}