package com.north.light.libareasel.bean.local;

import android.text.TextUtils;

import com.north.light.libareasel.bean.AddressDetailInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: AddressFullItemInfo
 * Author: lizhengting
 * Date: 2021/10/1 20:56
 * Description:全屏模式地址选择信息
 */
public class AddressLocalFullItemInfo implements Serializable {

    /**
     * 类型:1推荐 2内容 3标题
     */
    private int type = 1;

    /**
     * 显示内容--数据为外部
     */
    private AddressDetailInfo addressDetail;

    /**
     * 数据类型：0无值 1省 2市 3区
     */
    private int sourceType = 0;

    /**
     * 标题
     */
    private String title;


    /**
     * 推荐数据--数据为外部
     */
    private List<AddressDetailInfo> recommendList = new ArrayList<>();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 获取地址描述
     */
    public String getAddress() {
        if (addressDetail == null || TextUtils.isEmpty(addressDetail.getName())) {
            return "";
        }
        return addressDetail.getName();
    }

    public String getAddressId() {
        if (addressDetail == null || TextUtils.isEmpty(addressDetail.getId())) {
            return "";
        }
        return addressDetail.getId();
    }

    public AddressDetailInfo getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(AddressDetailInfo addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AddressDetailInfo> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<AddressDetailInfo> recommendList) {
        this.recommendList = recommendList;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceTYpe) {
        this.sourceType = sourceTYpe;
    }
}
