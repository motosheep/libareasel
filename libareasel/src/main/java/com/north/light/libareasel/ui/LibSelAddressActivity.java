package com.north.light.libareasel.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.north.light.libareasel.AddressMain;
import com.north.light.libareasel.R;
import com.north.light.libareasel.bean.AddressDetailInfo;
import com.north.light.libareasel.bean.AddressInfo;
import com.north.light.libareasel.bean.AddressSelResult;
import com.north.light.libareasel.constant.IntentCode;
import com.north.light.libareasel.model.AddressModel;
import com.north.light.libareasel.widget.AreaNumberPickerView;
import com.north.light.libareasel.widget.DivNumberPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibSelAddressActivity extends LibAddressBaseActivity {
    private static final String TAG = LibSelAddressActivity.class.getName();
    private DivNumberPicker mProvincePicker;
    private DivNumberPicker mCityPicker;
    private DivNumberPicker mDistrictPicker;

    //数据集合
    private ArrayList<AddressDetailInfo> mProvinceList = new ArrayList<>();
    private Map<String, ArrayList<AddressDetailInfo>> mCityMap = new HashMap<>();
    private Map<String, ArrayList<AddressDetailInfo>> mDistrictMap = new HashMap<>();

    private String[] mCityArray = new String[0];
    private String[] mDistrictArray = new String[0];
    private String[] provinceStrArray = new String[0];

    //当前选择的数据
    private String mCurSelProvince = "";
    private String mCurSelCity = "";
    private String mCurSelDistrict = "";

    private TextView mCancel;//取消
    private TextView mConfirm;//确定

    //动画
    private boolean isUserPickerAnim = true;//是否启用picker动画
    ValueAnimator mPickerAnim;

    /**
     * 显示的类型
     */
    private int mShowType = 1;
    /**
     * 数据来源--1本地 2自定义
     */
    private int mSourceType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_sel_address);
        mShowType = getIntent().getIntExtra(IntentCode.TYPE_DATA, 1);
        mSourceType = getIntent().getIntExtra(IntentCode.TYPE_SOURCE, 1);
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

        if (mShowType == 2) {
            mDistrictPicker.setVisibility(View.GONE);
        }
    }

    /**
     * 获取地址数据
     */
    private void getAddressData() {
        List<AddressInfo> result = new ArrayList();
        if (mSourceType == 1) {
            result = AddressModel.getInstance().getAddressData(this, "area.xml");
        } else if (mSourceType == 2) {
            result = AddressModel.getInstance().getAddressRemote();
        }
        //数据转换
        mProvinceList.clear();
        mCityMap.clear();
        mDistrictMap.clear();
        for (AddressInfo cache : result) {
            AddressDetailInfo provinceInfo = new AddressDetailInfo();
            provinceInfo.setId(cache.getId());
            provinceInfo.setName(cache.getName());
            mProvinceList.add(provinceInfo);
            mCityMap.putAll(cache.getCityMap());
            mDistrictMap.putAll(cache.getDistrictMap());
        }
        //转换完成__设置数据
        //省份
        provinceStrArray = trainAddressDetailInfoToStrArrays(mProvinceList);
        mProvincePicker.refreshByNewDisplayedValues(provinceStrArray);
        mProvincePicker.setMaxValue(provinceStrArray.length - 1); //设置最大值
        mProvincePicker.setWrapSelectorWheel(true);
        mCurSelProvince = provinceStrArray[0];
        //城市
        mCityArray = trainAddressDetailInfoToStrArrays(mCityMap.get(mCurSelProvince));
        mCityPicker.refreshByNewDisplayedValues(mCityArray);
        mCityPicker.setMaxValue(mCityArray.length - 1); //设置最大值
        mCityPicker.setWrapSelectorWheel(true);
        mCurSelCity = mCityArray[0];
        //市区
        mDistrictArray = trainAddressDetailInfoToStrArrays(mDistrictMap.get(mCurSelCity));
        mDistrictPicker.refreshByNewDisplayedValues(mDistrictArray);
        mDistrictPicker.setMaxValue(mDistrictArray.length - 1); //设置最大值
        mDistrictPicker.setWrapSelectorWheel(true);
        mCurSelDistrict = mDistrictArray[0];

        initEvent();
    }

    private void initEvent() {
        //监听事件
        mProvincePicker.setOnValueChangedListener(new AreaNumberPickerView.OnValueChangeListener() {

            /**
             * 每当选择的值改变时都会调用一次
             * @param oldVal 改变前的值
             * @param newVal 改变后的值
             */
            @Override
            public void onValueChange(AreaNumberPickerView picker, int oldVal, int newVal) {
                //改变市，区的值
                Log.d(TAG, "province: " + picker.getValue());
                mCurSelProvince = provinceStrArray[picker.getValue()];
                mCityArray = trainAddressDetailInfoToStrArrays(mCityMap.get(mCurSelProvince));
                mCityPicker.refreshByNewDisplayedValues(mCityArray);
                mCityPicker.setMaxValue(mCityArray.length - 1); //设置最大值

                mCurSelCity = mCityArray[0];
                mDistrictArray = trainAddressDetailInfoToStrArrays(mDistrictMap.get(mCurSelCity));
                mDistrictPicker.refreshByNewDisplayedValues(mDistrictArray);
                mDistrictPicker.setMaxValue(mDistrictArray.length - 1); //设置最大值
                mCurSelDistrict = mDistrictArray[0];
            }


        });
        mCityPicker.setOnValueChangedListener(new AreaNumberPickerView.OnValueChangeListener() {

            /**
             * 每当选择的值改变时都会调用一次
             * @param oldVal 改变前的值
             * @param newVal 改变后的值
             */
            @Override
            public void onValueChange(AreaNumberPickerView picker, int oldVal, int newVal) {
                Log.d(TAG, "city: " + picker.getValue());
                //获取当前位置,设置地区
                mCurSelCity = mCityArray[picker.getValue()];
                mDistrictArray = trainAddressDetailInfoToStrArrays(mDistrictMap.get(mCurSelCity));
                mDistrictPicker.refreshByNewDisplayedValues(mDistrictArray);
                mDistrictPicker.setMaxValue(mDistrictArray.length - 1); //设置最大值
                mCurSelDistrict = mDistrictArray[0];
            }

        });
        mDistrictPicker.setOnValueChangedListener(new AreaNumberPickerView.OnValueChangeListener() {


            /**
             * 每当选择的值改变时都会调用一次
             * @param oldVal 改变前的值
             * @param newVal 改变后的值
             */
            @Override
            public void onValueChange(AreaNumberPickerView picker, int oldVal, int newVal) {
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
                dealAddressWhenFinish();
            }
        });


        //滑动事件
        mProvincePicker.setOnScrollListener(new AreaNumberPickerView.OnScrollListener() {
            @Override
            public void onScrollStateChange(AreaNumberPickerView view, int scrollState) {
                Log.d(TAG, "mProvincePicker scrollState: " + scrollState);
                if (scrollState == 1 && isUserPickerAnim)
                    pickerAnim(1);
            }
        });
        mCityPicker.setOnScrollListener(new AreaNumberPickerView.OnScrollListener() {
            @Override
            public void onScrollStateChange(AreaNumberPickerView view, int scrollState) {
                Log.d(TAG, "mCityPicker scrollState: " + scrollState);
                if (scrollState == 1 && isUserPickerAnim)
                    pickerAnim(2);
            }
        });
        mDistrictPicker.setOnScrollListener(new AreaNumberPickerView.OnScrollListener() {
            @Override
            public void onScrollStateChange(AreaNumberPickerView view, int scrollState) {
                Log.d(TAG, "mDistrictPicker scrollState: " + scrollState);
                if (scrollState == 1 && isUserPickerAnim)
                    pickerAnim(3);
            }
        });
    }


    /**
     * 点击确认按钮，处理数据
     * private ArrayList<AddressDetailInfo> mProvinceList = new ArrayList<>();
     * private Map<String, ArrayList<AddressDetailInfo>> mCityMap = new HashMap<>();
     * private Map<String, ArrayList<AddressDetailInfo>> mDistrictMap = new HashMap<>();
     */
    private void dealAddressWhenFinish() {
        if (!TextUtils.isEmpty(mCurSelProvince) || !TextUtils.isEmpty(mCurSelCity) || !TextUtils.isEmpty(mCurSelDistrict)) {
            AddressSelResult result = new AddressSelResult();
            //省份
            result.setProvince(mCurSelProvince);
            for (AddressDetailInfo pInfo : mProvinceList) {
                if (pInfo.getName().equals(mCurSelProvince)) {
                    result.setProvinceId(pInfo.getId());
                }
            }
            //城市
            result.setCity(mCurSelCity);
            ArrayList<AddressDetailInfo> cList = mCityMap.get(mCurSelProvince);
            for (AddressDetailInfo cInfo : cList) {
                if (cInfo.getName().equals(mCurSelCity)) {
                    result.setCityId(cInfo.getId());
                }
            }
            //地区
            result.setDistrict(mCurSelDistrict);
            ArrayList<AddressDetailInfo> dList = mDistrictMap.get(mCurSelCity);
            for (AddressDetailInfo dInfo : dList) {
                if (dInfo.getName().equals(mCurSelDistrict)) {
                    result.setDistrictId(dInfo.getId());
                }
            }
            AddressMain.getInstance().onSelData(result);
        }
        finish();
    }

    /**
     * 点击number picker实现的动画
     *
     * @param type 1省 2市 3区
     */
    private void pickerAnim(final int type) {
        if (mPickerAnim != null) {
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


    /**
     * 传入AddressDetailInfo 集合转为 string[]
     */
    private String[] trainAddressDetailInfoToStrArrays(ArrayList<AddressDetailInfo> list) {
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).getName();
        }
        return result;
    }
}
