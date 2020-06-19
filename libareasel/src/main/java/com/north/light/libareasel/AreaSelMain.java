package com.north.light.libareasel;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by lzt
 * time 2020/6/19
 * 描述：地址选择，外部唯一调用的工具类
 */
public class AreaSelMain implements Serializable {
    //外部传入context
    private Context mContext;

    private static final class SingleHolder {
        private static final AreaSelMain mInstance = new AreaSelMain();
    }

    public static AreaSelMain getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 初始化————必须执行该方法，在使用前必须调用
     * */
    private void init(Context context){
        this.mContext = context.getApplicationContext();
    }
}
