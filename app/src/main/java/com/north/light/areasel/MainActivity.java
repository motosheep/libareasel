package com.north.light.areasel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.north.light.libareasel.AddressMain;
import com.north.light.libareasel.bean.AddressSelResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttondd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressMain.getInstance().show(MainActivity.this);
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
