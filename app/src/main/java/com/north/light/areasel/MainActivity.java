package com.north.light.areasel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.north.light.libareasel.bean.AddressInfo;
import com.north.light.libareasel.model.AddressModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<AddressInfo> result = AddressModel.getInstance().getAddressData(this, "area.xml");
        Log.d("MainActivity", "data: " + new Gson().toJson(result));
    }
}
