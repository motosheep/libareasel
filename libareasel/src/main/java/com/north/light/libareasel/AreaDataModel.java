package com.north.light.libareasel;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by lzt
 * time 2020/6/19
 * 描述：地址数据获取工具类
 */
public class AreaDataModel implements Serializable {
    private static final String TAG = AreaDataModel.class.getName();
    //外部传入context
    private Context mContext;
    //ui handler
    private Handler mUIHandler;
    //child handler
    private Handler mIOHandler;
    private HandlerThread mIOHandlerThread;

    private static final class SingleHolder {
        private static final AreaDataModel mInstance = new AreaDataModel();
    }

    public static AreaDataModel getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 初始化————必须执行该方法，在使用前必须调用
     * 必须调用，与release成对出现
     */
    private void init(Context context) {
        this.mContext = context.getApplicationContext();
        if (mUIHandler == null) {
            mUIHandler = new Handler(Looper.getMainLooper());
        }
        if (mIOHandlerThread == null) {
            mIOHandlerThread = new HandlerThread(AreaDataModel.class.getName() + "IOTHREAD");
        }
        if (mIOHandler == null) {
            mIOHandler = new Handler(mIOHandlerThread.getLooper());
        }
    }

    /**
     * 必须调用，与init成对出现
     */
    private void release() {
        try {
            if (mUIHandler != null) {
                mUIHandler.removeCallbacksAndMessages(null);
            }
            mUIHandler = null;
            if (mIOHandler != null) {
                mIOHandler.removeCallbacksAndMessages(null);
            }
            mIOHandler = null;
            if (mIOHandlerThread != null) {
                mIOHandlerThread.quit();
            }
            mIOHandlerThread = null;
        } catch (Exception e) {
            Log.d(TAG, "release error: " + e.getMessage());
        }
    }



}
