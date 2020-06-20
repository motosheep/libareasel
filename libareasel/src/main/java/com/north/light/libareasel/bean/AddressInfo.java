package com.north.light.libareasel.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:li
 * date:2020/6/21
 * desc:地址解释对象
 */
public class AddressInfo implements Serializable {

    //省
    private String province = "";
    //市
    private Map<String, List<String>> cityMap = new HashMap<>();
    //区
    private Map<String, List<String>> districtMap = new HashMap<>();

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Map<String, List<String>> getCityMap() {
        return cityMap;
    }

    public void setCityMap(Map<String, List<String>> cityMap) {
        this.cityMap = cityMap;
    }

    public Map<String, List<String>> getDistrictMap() {
        return districtMap;
    }

    public void setDistrictMap(Map<String, List<String>> districtMap) {
        this.districtMap = districtMap;
    }
}
