package com.north.light.libareasel.model;

import android.content.Context;

import com.north.light.libareasel.bean.AddressInfo;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * author:li
 * date:2020/6/21
 * desc:地址数据获取类
 */
public class AddressModel {

    private static final class SingleHolder {
        private static final AddressModel mInstance = new AddressModel();
    }

    public static AddressModel getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 获取地址数据
     * assets/area.xml
     */
    public List<AddressInfo> getAddressData(Context context, String path) {
        try {
            InputStream in = context.getResources().getAssets().open(path);
            List<AddressInfo> result = AddressModel.getInstance().readXmlBySAX(in);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 【SAX解析XML文件】
     **/
    private List<AddressInfo> readXmlBySAX(InputStream inputStream) {
        try {
            /**【创建解析器】**/
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            AddressSaxHandler handler = new AddressSaxHandler();
            saxParser.parse(inputStream, handler);
            inputStream.close();
            return handler.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
