package com.north.light.libareasel.model;

import android.text.TextUtils;

import com.north.light.libareasel.bean.AddressDetailInfo;
import com.north.light.libareasel.bean.AddressInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * author:li
 * date:2020/6/21
 * desc:地址model:xml解释
 */
public class AddressSaxHandler extends DefaultHandler {
    //解释结果结合
    private List<AddressInfo> result;
    private AddressInfo mCurrentInfo = new AddressInfo();

    private String mCurrentProvince = "";
    private String mCurrentCity = "";

    private String mTagName = "";

    public List<AddressInfo> getResult() {
        return result;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        result = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("province")) {
            mCurrentInfo = new AddressInfo();
            mCurrentInfo.setName(attributes.getValue("name"));
            mCurrentInfo.setId("0");
            mCurrentProvince = mCurrentInfo.getName();
        } else if (localName.equals("city")) {
            mCurrentCity = attributes.getValue("name");
            ArrayList<AddressDetailInfo> cityList = new ArrayList<>();
            if (mCurrentInfo.getCityMap().get(mCurrentProvince) == null) {
                AddressDetailInfo city = new AddressDetailInfo();
                city.setName(mCurrentCity);
                city.setId("0");
                cityList.add(city);
                mCurrentInfo.getCityMap().put(mCurrentProvince, cityList);
            } else {
                cityList = mCurrentInfo.getCityMap().get(mCurrentProvince);
                AddressDetailInfo city = new AddressDetailInfo();
                city.setName(mCurrentCity);
                city.setId("0");
                cityList.add(city);
                mCurrentInfo.getCityMap().put(mCurrentProvince, cityList);
            }
        }
        mTagName = localName;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (!TextUtils.isEmpty(mTagName)) {
            String data = new String(ch, start, length);
            if (mTagName.equals("district")) {
                ArrayList<AddressDetailInfo> districtList = new ArrayList<>();
                if (mCurrentInfo.getDistrictMap().get(mCurrentCity) == null) {
                    AddressDetailInfo dist = new AddressDetailInfo();
                    dist.setName(data);
                    dist.setId("0");
                    districtList.add(dist);
                    mCurrentInfo.getDistrictMap().put(mCurrentCity, districtList);
                } else {
                    districtList = mCurrentInfo.getDistrictMap().get(mCurrentCity);
                    AddressDetailInfo dist = new AddressDetailInfo();
                    dist.setName(data);
                    dist.setId("0");
                    districtList.add(dist);
                    mCurrentInfo.getDistrictMap().put(mCurrentCity, districtList);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals("province")) {
            result.add(mCurrentInfo);
        }
        this.mTagName = null;
    }
}
