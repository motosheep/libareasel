package com.north.light.libareasel.bean;

import java.io.Serializable;

/**
 * author:li
 * date:2020/6/21
 * desc:地址选择结果
 */
public class AddressSelResult implements Serializable {
    private String province;//省
    private String city;//市
    private String district;//区

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
