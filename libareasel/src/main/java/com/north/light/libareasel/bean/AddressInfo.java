package com.north.light.libareasel.bean;

import java.io.Serializable;
import java.util.ArrayList;
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
    private String id = "";
    private String name = "";
    //市
    private Map<String, ArrayList<AddressDetailInfo>> cityMap = new HashMap<>();
    //区
    private Map<String, ArrayList<AddressDetailInfo>> districtMap = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ArrayList<AddressDetailInfo>> getCityMap() {
        return cityMap;
    }

    public void setCityMap(Map<String, ArrayList<AddressDetailInfo>> cityMap) {
        this.cityMap = cityMap;
    }

    public Map<String, ArrayList<AddressDetailInfo>> getDistrictMap() {
        return districtMap;
    }

    public void setDistrictMap(Map<String, ArrayList<AddressDetailInfo>> districtMap) {
        this.districtMap = districtMap;
    }
}
