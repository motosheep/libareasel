package com.north.light.libareasel;

import android.app.Activity;
import android.content.Intent;

import com.north.light.libareasel.bean.AddressSelResult;
import com.north.light.libareasel.ui.LibSelAddressActivity;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * author:li
 * date:2020/6/21
 * desc:
 */
public class AddressMain implements Serializable {
    private CopyOnWriteArrayList<AddressSelInfoCallBack> mListenerList = new CopyOnWriteArrayList<>();


    private static final class SingleHolder {
        private static final AddressMain mInstance = new AddressMain();
    }

    public static AddressMain getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 回调的结果
     */
    public void onSelData(AddressSelResult result) {
        if (result != null && mListenerList.size() != 0) {
            for (AddressSelInfoCallBack listener : mListenerList) {
                if (listener != null) {
                    listener.Address(result);
                }
            }
        }
    }

    /**
     * 启动地址选择
     * 启动地址选择activity,start for result
     */
    public void show(Activity activity) {
        Intent intent = new Intent(activity, LibSelAddressActivity.class);
        activity.startActivity(intent);
    }


    /**
     * 地址选择回调-------------------------------------------------------------------------------
     */
    public interface AddressSelInfoCallBack {
        void Address(AddressSelResult result);
    }

    public void setOnAddressListener(AddressSelInfoCallBack mListener) {
        if (mListener == null) {
            return;
        }
        mListenerList.add(mListener);
    }

    public void removeAddressListener(AddressSelInfoCallBack mListener) {
        if (mListener == null) {
            return;
        }
        mListenerList.remove(mListener);
    }


}
