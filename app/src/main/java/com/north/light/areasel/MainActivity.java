package com.north.light.areasel;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.north.light.libareasel.AddressMain;
import com.north.light.libareasel.bean.AddressSelResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttondd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddressMain.getInstance().show(MainActivity.this);
                AddressMain.getInstance().showFull(MainActivity.this, 1, new ArrayList());
            }
        });
        AddressMain.getInstance().setOnAddressListener(new AddressMain.AddressSelInfoCallBack() {
            @Override
            public void Address(AddressSelResult result) {
                Log.d("MainA", "选择了: " + new Gson().toJson(result));
            }
        });
    }
}
