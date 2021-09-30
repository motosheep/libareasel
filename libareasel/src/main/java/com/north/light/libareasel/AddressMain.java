package com.north.light.libareasel;

import android.app.Activity;
import android.content.Intent;

import com.north.light.libareasel.bean.AddressDetailInfo;
import com.north.light.libareasel.bean.AddressInfo;
import com.north.light.libareasel.bean.AddressSelResult;
import com.north.light.libareasel.constant.IntentCode;
import com.north.light.libareasel.model.AddressModel;
import com.north.light.libareasel.ui.LibSelAddressActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
        show(activity, 1);
    }

    public void show(Activity activity, int type) {
        Intent intent = new Intent(activity, LibSelAddressActivity.class);
        intent.putExtra(IntentCode.TYPE_DATA, type);
        activity.startActivity(intent);
    }

    /**
     * @param source 数据来源:1本地 2传入
     */
    public void show(Activity activity, int type, List<AddressInfo> source) {
        AddressModel.getInstance().setRemoteData(source);
        Intent intent = new Intent(activity, LibSelAddressActivity.class);
        intent.putExtra(IntentCode.TYPE_DATA, type);
        intent.putExtra(IntentCode.TYPE_SOURCE, 2);
        activity.startActivity(intent);
    }

    /**
     * 传入 List<AddressInfo> source,已选择的省市区，返回省市区的具体信息
     */
    public HashMap<String, AddressDetailInfo> trainCity(List<AddressInfo> source, String selPro
            , String selCity, String selDistrict) {
        if (source == null) {
            return new HashMap<>();
        }
        //获取对应省市区的id
        List<AddressInfo> provinceData = source;
        AddressInfo provinceInfo = new AddressInfo();
        for (AddressInfo pCache : provinceData) {
            if (pCache.getName().contains(selPro) ||
                    selPro.contains(pCache.getName())) {
                provinceInfo = pCache;
                break;
            }
        }
        String selProName = provinceInfo.getName();
        String selProId = provinceInfo.getId();
        AddressDetailInfo cityInfo = new AddressDetailInfo();
        List<AddressDetailInfo> cityData = provinceInfo.getCityMap().get(selProName);
        for (AddressDetailInfo cCache : cityData) {
            if (cCache.getName().contains(selCity) ||
                    selCity.contains(cCache.getName())) {
                cityInfo = cCache;
                break;
            }
        }
        String selCityName = cityInfo.getName();
        String selCityId = cityInfo.getId();
        AddressDetailInfo districtInfo = new AddressDetailInfo();
        List<AddressDetailInfo> distData = provinceInfo.getDistrictMap().get(selCityName);
        for (AddressDetailInfo dCache : distData) {
            if (dCache.getName().contains(selDistrict) ||
                    selDistrict.contains(dCache.getName())) {
                districtInfo = dCache;
                break;
            }
        }
        String selDisName = districtInfo.getName();
        String selDisId = districtInfo.getId();
        //汇总数据
        AddressDetailInfo provinceResult = new AddressDetailInfo();
        provinceResult.setName(selProName);
        provinceResult.setId(selProId);

        AddressDetailInfo cityResult = new AddressDetailInfo();
        cityResult.setName(selCityName);
        cityResult.setId(selCityId);

        AddressDetailInfo disResult = new AddressDetailInfo();
        disResult.setName(selDisName);
        disResult.setId(selDisId);

        HashMap<String, AddressDetailInfo> result = new HashMap<>();
        result.put("province", provinceResult);
        result.put("city", cityResult);
        result.put("district", disResult);
        return result;
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
