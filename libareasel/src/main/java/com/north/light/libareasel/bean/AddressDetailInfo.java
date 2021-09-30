package com.north.light.libareasel.bean;

import java.io.Serializable;

/**
 * @Author: lzt
 * @Date: 2021/9/17 9:31
 * @Description:地址详细信息
 */
public class AddressDetailInfo implements Serializable {

    private String id = "";
    private String name = "";

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
}
