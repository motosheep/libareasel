package com.north.light.libareasel.ui.full;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.north.light.libareasel.R;
import com.north.light.libareasel.constant.AddressIntentCode;
import com.north.light.libareasel.ui.base.LibAddressBaseActivity;
import com.north.light.libareasel.utils.AddressStatusBarUtils;


/**
 * 全屏地址选择activity
 */
public class LibSelFullAddressActivity extends LibAddressBaseActivity {

    /**
     * 数据类型：1本地 2远程
     */
    private int mDateType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_sel_full_address);
        mDateType = getIntent().getIntExtra(AddressIntentCode.TYPE_FULL_SOURCE, -1);
        if (mDateType == -1) {
            Toast.makeText(this.getApplicationContext(), "参数错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        AddressStatusBarUtils.setStatusTextColor(this, true);
        initView();
        initEvent();
    }

    private void initView() {
        View bar = findViewById(R.id.activity_lib_sel_full_address_bar);
        LinearLayout.LayoutParams barParams = (LinearLayout.LayoutParams) bar.getLayoutParams();
        barParams.height = AddressStatusBarUtils.getStatusBarHeight(this);
        bar.setLayoutParams(barParams);
        //加载fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_lib_sel_full_address_content,
                LibSelAddressFullFragment.newInstance(mDateType)).commitAllowingStateLoss();
    }

    private void initEvent() {
        findViewById(R.id.activity_lib_sel_full_address_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}