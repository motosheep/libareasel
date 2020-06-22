package com.north.light.libareasel.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.north.light.libareasel.AddressMain;
import com.north.light.libareasel.R;
import com.north.light.libareasel.bean.AddressInfo;
import com.north.light.libareasel.bean.AddressSelResult;
import com.north.light.libareasel.model.AddressModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibSelAddressActivity extends LibAddressBaseActivity {
    private static final String TAG = LibSelAddressActivity.class.getName();
    private NumberPicker mProvincePicker;
    private NumberPicker mCityPicker;
    private NumberPicker mDistrictPicker;

    //数据集合
    private List<String> mProvinceList = new ArrayList<>();
    private Map<String, List<String>> mCityMap = new HashMap<>();
    private Map<String, List<String>> mDistrictMap = new HashMap<>();

    private String[] mCityArray;
    private String[] mDistrictArray;
    private String[] provinceStrArray;

    //当前选择的数据
    private String mCurSelProvince = "";
    private String mCurSelCity = "";
    private String mCurSelDistrict = "";

    private TextView mCancel;//取消
    private TextView mConfirm;//确定

    //动画
    private boolean isUserPickerAnim = true;//是否启用picker动画
    ValueAnimator mPickerAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_sel_address);
        initView();
        getAddressData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mProvincePicker = findViewById(R.id.activity_lib_sel_address_content_province);
        mCityPicker = findViewById(R.id.activity_lib_sel_address_content_city);
        mDistrictPicker = findViewById(R.id.activity_lib_sel_address_content_district);
        mCancel = findViewById(R.id.activity_lib_sel_address_cancel);
        mConfirm = findViewById(R.id.activity_lib_sel_address_confirm);
    }

    /**
     * 获取地址数据
     */
    private void getAddressData() {
        List<AddressInfo> result = AddressModel.getInstance().getAddressData(this, "area.xml");
        //数据转换
        for (AddressInfo cache : result) {
            mProvinceList.add(cache.getProvince());
            mCityMap.putAll(cache.getCityMap());
            mDistrictMap.putAll(cache.getDistrictMap());
        }
        //转换完成__设置数据
        //省份
        provinceStrArray = mProvinceList.toArray(new String[mProvinceList.size()]);
        mProvincePicker.setDisplayedValues(provinceStrArray);
        mProvincePicker.setMaxValue(provinceStrArray.length - 1); //设置最大值，最大值是datas[3]
        mProvincePicker.setWrapSelectorWheel(true);
        mProvincePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入
        mCurSelProvince = provinceStrArray[0];
        //城市
        mCityArray = mCityMap.get(mCurSelProvince).toArray(new String[mCityMap.get(mCurSelProvince).size()]);
        mCityPicker.setDisplayedValues(mCityArray);
        mCityPicker.setMaxValue(mCityArray.length - 1); //设置最大值
        mCityPicker.setWrapSelectorWheel(true);
        mCityPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入
        mCurSelCity = mCityArray[0];
        //市区
        mDistrictArray = mDistrictMap.get(mCurSelCity).toArray(new String[mDistrictMap.get(mCurSelCity).size()]);
        mDistrictPicker.setDisplayedValues(mDistrictArray);
        mDistrictPicker.setMaxValue(mDistrictArray.length - 1); //设置最大值
        mDistrictPicker.setWrapSelectorWheel(true);
        mDistrictPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入
        mCurSelDistrict = mDistrictArray[0];

        initEvent();
    }

    private void initEvent() {
        //监听事件
        mProvincePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            /**
             * 每当选择的值改变时都会调用一次
             * @param oldVal 改变前的值
             * @param newVal 改变后的值
             */
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //改变市，区的值
                Log.d(TAG, "province: " + picker.getValue());
                mCurSelProvince = provinceStrArray[picker.getValue()];
                mCityArray = mCityMap.get(mCurSelProvince).toArray(new String[mCityMap.get(mCurSelProvince).size()]);
                mCityPicker.setDisplayedValues(null);
                mCityPicker.setMaxValue(mCityArray.length - 1); //设置最大值
                mCityPicker.setDisplayedValues(mCityArray);

                mCurSelCity = mCityArray[0];
                mDistrictArray = mDistrictMap.get(mCurSelCity).toArray(new String[mDistrictMap.get(mCurSelCity).size()]);
                mDistrictPicker.setDisplayedValues(null);
                mDistrictPicker.setMaxValue(mDistrictArray.length - 1); //设置最大值
                mDistrictPicker.setDisplayedValues(mDistrictArray);

            }
        });
        mCityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            /**
             * 每当选择的值改变时都会调用一次
             * @param oldVal 改变前的值
             * @param newVal 改变后的值
             */
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Log.d(TAG, "city: " + picker.getValue());
                //获取当前位置,设置地区
                mCurSelCity = mCityArray[picker.getValue()];
                mDistrictArray = mDistrictMap.get(mCurSelCity).toArray(new String[mDistrictMap.get(mCurSelCity).size()]);
                mDistrictPicker.setDisplayedValues(null);
                mDistrictPicker.setMaxValue(mDistrictArray.length - 1); //设置最大值
                mDistrictPicker.setDisplayedValues(mDistrictArray);

            }
        });
        mDistrictPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            /**
             * 每当选择的值改变时都会调用一次
             * @param oldVal 改变前的值
             * @param newVal 改变后的值
             */
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Log.d(TAG, "district: " + picker.getValue());
                mCurSelDistrict = mDistrictArray[picker.getValue()];
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mCurSelProvince) || !TextUtils.isEmpty(mCurSelCity) || !TextUtils.isEmpty(mCurSelDistrict)) {
                    AddressSelResult result = new AddressSelResult();
                    result.setProvince(mCurSelProvince);
                    result.setCity(mCurSelCity);
                    result.setDistrict(mCurSelDistrict);
                    AddressMain.getInstance().onSelData(result);
                }
                finish();
            }
        });


        //滑动事件
        mProvincePicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                Log.d(TAG, "mProvincePicker scrollState: " + scrollState);
                if (scrollState == 1 && isUserPickerAnim)
                    pickerAnim(1);
            }
        });
        mCityPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                Log.d(TAG, "mCityPicker scrollState: " + scrollState);
                if (scrollState == 1 && isUserPickerAnim)
                    pickerAnim(2);
            }
        });
        mDistrictPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                Log.d(TAG, "mDistrictPicker scrollState: " + scrollState);
                if (scrollState == 1 && isUserPickerAnim)
                    pickerAnim(3);
            }
        });
    }

    /**
     * 点击number picker实现的动画
     *
     * @param type 1省 2市 3区
     */
    private void pickerAnim(final int type) {
        if (mPickerAnim != null) {
//            mPickerAnim.cancel();
            mPickerAnim.end();
        }
        mPickerAnim = ValueAnimator.ofFloat(0f, 1f);
        mPickerAnim.setDuration(300);
        mPickerAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.d("TAG", "cuurent value is " + currentValue);
                if (type == 1) {
                    //市 区 的weight渐变为1，省的渐变为2
                    LinearLayout.LayoutParams mProvinceParams = (LinearLayout.LayoutParams) mProvincePicker.getLayoutParams();
                    LinearLayout.LayoutParams mCityParams = (LinearLayout.LayoutParams) mCityPicker.getLayoutParams();
                    LinearLayout.LayoutParams mDistrictParams = (LinearLayout.LayoutParams) mDistrictPicker.getLayoutParams();
                    if (mProvinceParams.weight < 2) {
                        mProvinceParams.weight = (mProvinceParams.weight + currentValue);
                        mProvincePicker.setLayoutParams(mProvinceParams);
                    }
                    if (mCityParams.weight > 1) {
                        mCityParams.weight = (mCityParams.weight - currentValue);
                        mCityPicker.setLayoutParams(mCityParams);
                    }
                    if (mDistrictParams.weight > 1) {
                        mDistrictParams.weight = (mDistrictParams.weight - currentValue);
                        mDistrictPicker.setLayoutParams(mDistrictParams);
                    }
                } else if (type == 2) {
                    //市 区 的weight渐变为1，省的渐变为2
                    LinearLayout.LayoutParams mProvinceParams = (LinearLayout.LayoutParams) mProvincePicker.getLayoutParams();
                    LinearLayout.LayoutParams mCityParams = (LinearLayout.LayoutParams) mCityPicker.getLayoutParams();
                    LinearLayout.LayoutParams mDistrictParams = (LinearLayout.LayoutParams) mDistrictPicker.getLayoutParams();
                    if (mCityParams.weight < 2) {
                        mCityParams.weight = (mCityParams.weight + currentValue);
                        mCityPicker.setLayoutParams(mCityParams);
                    }
                    if (mProvinceParams.weight > 1) {
                        mProvinceParams.weight = (mProvinceParams.weight - currentValue);
                        mProvincePicker.setLayoutParams(mProvinceParams);
                    }
                    if (mDistrictParams.weight > 1) {
                        mDistrictParams.weight = (mDistrictParams.weight - currentValue);
                        mDistrictPicker.setLayoutParams(mDistrictParams);
                    }
                } else if (type == 3) {
                    //市 区 的weight渐变为1，省的渐变为2
                    LinearLayout.LayoutParams mProvinceParams = (LinearLayout.LayoutParams) mProvincePicker.getLayoutParams();
                    LinearLayout.LayoutParams mCityParams = (LinearLayout.LayoutParams) mCityPicker.getLayoutParams();
                    LinearLayout.LayoutParams mDistrictParams = (LinearLayout.LayoutParams) mDistrictPicker.getLayoutParams();
                    if (mDistrictParams.weight < 2) {
                        mDistrictParams.weight = (mDistrictParams.weight + currentValue);
                        mDistrictPicker.setLayoutParams(mDistrictParams);
                    }
                    if (mProvinceParams.weight > 1) {
                        mProvinceParams.weight = (mProvinceParams.weight - currentValue);
                        mProvincePicker.setLayoutParams(mProvinceParams);
                    }
                    if (mCityParams.weight > 1) {
                        mCityParams.weight = (mCityParams.weight - currentValue);
                        mCityPicker.setLayoutParams(mCityParams);
                    }
                }
            }
        });
        mPickerAnim.start();
    }

}
